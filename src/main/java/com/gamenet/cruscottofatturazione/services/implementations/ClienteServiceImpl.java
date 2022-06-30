package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.context.QuerySpecification;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;
import com.gamenet.cruscottofatturazione.repositories.ClienteRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService
{
	
    private final ClienteRepository clienteRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
    
	@Override
	public List<Cliente> getClienti() {
		return clienteRepository.getClienti();
	}
	
	@Override
	public List<Cliente> getClienti(String codiceSocieta) {
		return clienteRepository.getClientiBySocieta(codiceSocieta);
	}

	@Override
	public Cliente getClienteById(Integer clienteId) {
		return clienteRepository.findById(clienteId).orElse(null);
	}

	@Override
	public Boolean saveCliente(Cliente cliente, String utenteUpdate) {
    	this.log.info("ClienteService: saveCliente -> START");
    	appService.insertLog("info", "ClienteService", "saveCliente", "START", "", "saveCliente");

    	try
		{	
    		if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(cliente);
		    	this.log.debug("ProspectService: saveCliente -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveCliente", "Object: " + requestPrint, "", "saveCliente");
			}
        	
    		if(cliente.getId()==null) {
    			cliente.setCreate_date(new Date());
    			cliente.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			cliente.setLast_mod_user(utenteUpdate);
    			cliente.setLast_mod_date(new Date());
    			
    		}
    		
    		clienteRepository.save(cliente);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: saveCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "saveCliente", "Exception", stackTrace, "saveCliente");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ClienteService: saveCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "saveCliente", "SUCCESSFULLY END", "", "saveCliente");
    	return true;
	}

	@Override
	public Boolean deleteCliente(Integer clienteId, String utenteUpdate) {
		this.log.info("ClienteService: deleteCliente -> [clienteId: " + clienteId.toString() + "]");
    	appService.insertLog("info", "ClienteService", "deleteCliente", "[clienteId: " + clienteId.toString() + "]", "", "deleteCliente");
    	
    	try
		{	
    		 Cliente cliente= clienteRepository.findById(clienteId).orElse(null);
    		 
    		 if(cliente!=null) {
    			 //TODO: verificare se ci sono Clienti con cliente
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
