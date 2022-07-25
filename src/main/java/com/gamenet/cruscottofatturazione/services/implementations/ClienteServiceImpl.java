package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.Enum.EsitoSap;
import com.gamenet.cruscottofatturazione.context.QuerySpecification;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.response.ClienteAutoComplete;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;
import com.gamenet.cruscottofatturazione.models.response.SaveResponseCliente;
import com.gamenet.cruscottofatturazione.models.sap.request.CustomerRequest;
import com.gamenet.cruscottofatturazione.models.sap.response.CustomerResponse;
import com.gamenet.cruscottofatturazione.models.sap.response.E_CUSTOMER_DATA;
import com.gamenet.cruscottofatturazione.repositories.ClienteRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService
{
	@Value("${sap.url.customer.service}")
    private String urlCustomerServiceSap;
	private final ClienteRepository clienteRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    
	@Override
	public List<Cliente> getClienti() {
		return clienteRepository.getClienti();
	}
	
	@Override
	public List<Cliente> getClienti(String codiceSocieta) {
		return clienteRepository.getClientiBySocieta(codiceSocieta);
	}
	
	@Override
	public List<ClienteAutoComplete> getActiveClienti(String codiceSocieta) {
		List<ClienteAutoComplete> listClienti= new ArrayList<>();
		List<Cliente> clienti=  clienteRepository.getActiveClientiBySocieta(codiceSocieta);
		for (Cliente cliente : clienti) {
			listClienti.add(new ClienteAutoComplete(cliente.getCodiceCliente(), cliente.getRagioneSociale(), cliente.getPartitaIva()));
		}
		return listClienti;
	}
	

	@Override
	public Cliente getClienteById(String codiceCliente) {
		return clienteRepository.findByCodiceCliente(codiceCliente);
	}

	@Override
	public SaveResponse updateCliente(Cliente cliente, String utenteUpdate) {
    	this.log.info("ClienteService: updateCliente -> START");
    	appService.insertLog("info", "ClienteService", "updateCliente", "START", "", "updateCliente");
    	SaveResponse response = new SaveResponse();
    	try
		{	
    		if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(cliente);
		    	this.log.debug("ProspectService: saveCliente -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "updateCliente", "Object: " + requestPrint, "", "updateCliente");
			}
    		
    		Cliente clienteDb= clienteRepository.findById(cliente.getCodiceCliente()).orElse(null);
    		if(clienteDb==null) {
    			response.setEsito(false);
    			response.setErrore("cliente non trovato!");
    			
    		}
    		else
    		{
    			cliente.setLast_mod_user(utenteUpdate);
    			cliente.setLast_mod_date(new Date());
    			response.setEsito(true);
    		}
    		if(response.getEsito()==true)
    			clienteRepository.save(cliente);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: updateCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "updateCliente", "Exception", stackTrace, "updateCliente");
			
	        e.printStackTrace();
	        response.setEsito(false);
	        response.setErrore(e.getMessage());
		}
    	
    	this.log.info("ClienteService: saveCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "saveCliente", "SUCCESSFULLY END", "", "saveCliente");
    	return response;
	}
	
	
	@Override
	public SaveResponseCliente saveCliente(Cliente cliente, String utenteUpdate) {
    	this.log.info("ClienteService: saveCliente -> START");
    	appService.insertLog("info", "ClienteService", "saveCliente", "START", "", "saveCliente");
    	SaveResponseCliente response = new SaveResponseCliente();
    	try
		{	
    		if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(cliente);
		    	this.log.debug("ProspectService: saveCliente -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveCliente", "Object: " + requestPrint, "", "saveCliente");
			}
    		
    		
    		if(cliente.getCodiceCliente()==null)
    			throw new Exception("il codice cliente e' obbligatorio!");
    		
    		if(cliente.getSocieta()==null)
    			throw new Exception("il codice societa' e' obbligatorio!");
    		
    		Cliente clienteDb= clienteRepository.findById(cliente.getCodiceCliente()).orElse(null);
        	
    		if(clienteDb==null) {
    			
    			getClienteDaSap(cliente);
    			
    			cliente.setCreate_date(new Date());
    			cliente.setCreate_user(utenteUpdate);
    			response.setEsito(true);
    		}
    		else
    		{
    			response.setEsito(false);
    			response.setErrore("Codice cliente gia' presente!");
    			this.log.error("ClienteService: saveCliente -> " + "codice cliente gia' presente!");
    		}
    		
    		if(response.getEsito()==true) {
    			
    			cliente=clienteRepository.save(cliente);
    			com.gamenet.cruscottofatturazione.models.Cliente clienteReturn= new com.gamenet.cruscottofatturazione.models.Cliente();
    			BeanUtils.copyProperties(cliente, clienteReturn);
    			response.setCliente(clienteReturn);
    		}
    		
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: saveCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "saveCliente", "Exception", stackTrace, "saveCliente");
			
	        e.printStackTrace();
	        response.setEsito(false);
	        response.setErrore(e.getMessage());
		}

    	this.log.info("ClienteService: saveCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "saveCliente", "SUCCESSFULLY END", "", "saveCliente");
    	return response;
	}

	private void getClienteDaSap(Cliente cliente) throws Exception {
		
		this.log.info("ClienteService: getClienteDaSap -> START");
    	appService.insertLog("info", "ClienteService", "getClienteDaSap", "START", "", "getClienteDaSap");
		CustomerRequest request = new CustomerRequest(cliente.getCodiceCliente(),cliente.getSocieta());


		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<CustomerRequest> entityRequest = new HttpEntity<CustomerRequest>(request, headers);
			
			ResponseEntity<CustomerResponse> customerResponse=	restTemplate.postForEntity(urlCustomerServiceSap, entityRequest, CustomerResponse.class);
			this.log.info("ResponseCode: "+customerResponse.getStatusCode().toString());
			if(customerResponse.getStatusCode().equals(HttpStatus.OK)) {


				E_CUSTOMER_DATA customerSap= customerResponse.getBody().getE_CUSTOMER_DATA();
			
				this.log.info("ClienteService: getClienteDaSap -> esito: " + customerSap.getESITO() +" - idMessaggio: " + customerSap.getIDMESSAGGIO()+ " - message: "+customerSap.getDESCRIZIONE());
				
				if(customerSap.getESITO().equals(EsitoSap.SUCCESSO.getValue())) {
					cliente.setRagioneSociale(customerSap.getNAME());
					cliente.setCodiceFiscale(customerSap.getSTCD1());
					cliente.setPartitaIva(customerSap.getSTCD2());
					cliente.setNazionalita(customerSap.getLAND1());
					cliente.setSedeLegale(customerSap.getADDRESS());
					cliente.setAppartieneGruppoIva(customerSap.getZGIVA());
					cliente.setCodiceDestinatarioFatturazione(customerSap.getSTCD4());
					cliente.setModalitaPagamento(customerSap.getZWELS());
					cliente.setCondizioniPagamento(customerSap.getZTERM());
				}

				else {
					
					throw new Exception("Non è stato possibile acquisire il cliente.");
				}

			}else {
				throw new Exception("Non è stato possibile acquisire il cliente.");
			}
		} catch (Throwable e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: getClienteDaSap -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "getClienteDaSap", "Exception", stackTrace, "getClienteDaSap");
			
			throw new Exception("Non è stato possibile acquisire il cliente");
		} 
		
		this.log.info("ClienteService: getClienteDaSap -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "getClienteDaSap", "SUCCESSFULLY END", "", "getClienteDaSap");

	}

	@Override
	public Boolean deleteCliente(String codiceCliente, String utenteUpdate) {
		this.log.info("ClienteService: deleteCliente -> [codiceCliente: " + codiceCliente.toString() + "]");
    	appService.insertLog("info", "ClienteService", "deleteCliente", "[codiceCliente: " + codiceCliente.toString() + "]", "", "deleteCliente");
    	
    	try
		{	
    		 Cliente cliente= clienteRepository.findById(codiceCliente).orElse(null);
    		 
    		 if(cliente!=null) {
    			 //TODO: verificare se ci sono Fatture con cliente
    			// clienteRepository.deleteCliente(clienteId);
    			 clienteRepository.delete(cliente);
    		 }
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: deleteCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "deleteCliente", "Exception", stackTrace, "deleteCliente");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ClienteService: deleteCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "deleteCliente", "SUCCESSFULLY END", "", "deleteCliente");
    	return true;
	}

	@Override
	public ClientiListOverview getClientiDataTable(JsonNode payload) {
		this.log.info("ClientiService: getClientiDataTable -> START");
    	appService.insertLog("info", "ClientiService", "getClientiDataTable", "START", "", "getClientiDataTable");

    	ClientiListOverview response = new ClientiListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<com.gamenet.cruscottofatturazione.models.Cliente>());
		
		try
		{
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(payload);
		    	this.log.debug("ClientiService: getUtentiDataTable -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ClientiService", "getClientiDataTable", "Object: " + requestPrint, "", "getClientiDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());
			
			Specification<com.gamenet.cruscottofatturazione.entities.Cliente> spec = new QuerySpecification<>();

			for (ListFilter filter : model.getFilters())
				spec = spec.and(new QuerySpecification<>(filter));

			PageRequest request = null;
			if (model.getSort() != null && !model.getSort().isEmpty()) {
				int index = 0;
				Sort sort = null;
				for (ListSort srt : model.getSort()) {
					if (index++ == 0)
						sort = Sort.by(SortUtils.translateSort(srt.getType()), srt.getName());
					else
						sort = sort.and(Sort.by(SortUtils.translateSort(srt.getType()), srt.getName()));
				}

				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE, sort);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize(), sort);
				}
			} else {
				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize());
				}
			}

			Page<com.gamenet.cruscottofatturazione.entities.Cliente> pages = clienteRepository.findAll(spec, request);
			if (pages != null && pages.getTotalElements() > 0)
			{
				response.setTotalCount((int) pages.getTotalElements());
				
				for (com.gamenet.cruscottofatturazione.entities.Cliente ent_cli : pages.getContent()) {
					com.gamenet.cruscottofatturazione.models.Cliente mod_cli = new com.gamenet.cruscottofatturazione.models.Cliente();
					BeanUtils.copyProperties(ent_cli, mod_cli);
					
					response.getLines().add(mod_cli);
				}		
			}
	    	
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(response);
		    	this.log.debug("ClientiService: getClientiDataTable -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "ClientiService", "getClientiDataTable", "PROCESS END WITH: " + responsePrint, "", "getClientiDataTable");
			}
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClientiService: getClientiDataTable -> " + stackTrace);
			appService.insertLog("error", "ClientiService", "getClientiDataTable", "Exception", stackTrace, "getClientiDataTable");
			
			throw new RuntimeException(e);
		}
   
		return response;
	}



	
	
}
