package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.Enum.StatoFattura;
import com.gamenet.cruscottofatturazione.context.QuerySpecification;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.StatoFatturaLog;
import com.gamenet.cruscottofatturazione.models.Cliente;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.response.FattureListOverview;
import com.gamenet.cruscottofatturazione.repositories.ClienteRepository;
import com.gamenet.cruscottofatturazione.repositories.FatturaRepository;
import com.gamenet.cruscottofatturazione.repositories.StatoFatturaLogRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.DettaglioFatturaService;
import com.gamenet.cruscottofatturazione.services.interfaces.FatturaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FatturaServiceImpl implements FatturaService
{

	private final FatturaRepository fatturaRepository;
	private final StatoFatturaLogRepository statoFatturaLogRepository;
	private final ClienteRepository clienteRepository;
	private Logger log = LoggerFactory.getLogger(getClass());
	private final ApplicationLogsService appService;
	private final Environment env;
	private ObjectMapper jsonMapper = new ObjectMapper();

	private final DettaglioFatturaService dettaglioFatturaService;

	@Override
	public List<Fattura> getFatture() {
		return fatturaRepository.getFatture();
	}

	@Override
	public List<com.gamenet.cruscottofatturazione.models.Fattura> getFatture(String societa) {
		List<Fattura> fatture = fatturaRepository.getFattureBySocieta(societa);

		List<com.gamenet.cruscottofatturazione.models.Fattura> fattureRet= new ArrayList<>();
		for (Fattura fattura : fatture) {
			com.gamenet.cruscottofatturazione.models.Fattura fatRet= new com.gamenet.cruscottofatturazione.models.Fattura();
			BeanUtils.copyProperties(fattura, fatRet);
			fattureRet.add(fatRet);
		}

		return fattureRet;
	}


	@Override
	public Fattura getFatturaById(Integer fatturaId) {
		return fatturaRepository.findById(fatturaId).orElse(null);
	}


	@Override
	public Boolean saveFattura(Fattura fattura, String utenteUpdate) {
		this.log.info("FatturaService: saveFattura -> START");
		appService.insertLog("info", "FatturaService", "saveFattura", "START", "", "saveFattura");
		//Fattura fatturaSaved=null;
		try
		{	
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String requestPrint = jsonMapper.writeValueAsString(fattura);
				this.log.debug("ProspectService: saveFattura -> Object: " + requestPrint);
				appService.insertLog("debug", "ProspectService", "saveFattura", "Object: " + requestPrint, "", "saveFattura");
			}

			if(fattura.getId()==null) {
				fattura.setCreate_date(new Date());
				fattura.setCreate_user(utenteUpdate);
			}
			else
			{
				fattura.setLast_mod_user(utenteUpdate);
				fattura.setLast_mod_date(new Date());

			}

			//fatturaSaved=fatturaRepository.save(fattura);
			fatturaRepository.save(fattura);
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FatturaService: saveFattura -> " + stackTrace);
			appService.insertLog("error", "FatturaService", "saveFattura", "Exception", stackTrace, "saveFattura");

			e.printStackTrace();
			return false;
		}

		this.log.info("FatturaService: saveFattura -> SUCCESSFULLY END");
		appService.insertLog("info", "FatturaService", "saveFattura", "SUCCESSFULLY END", "", "saveFattura");
		return true;
	}

	@Override
	public com.gamenet.cruscottofatturazione.models.Fattura saveFatturaConDettagli(com.gamenet.cruscottofatturazione.models.Fattura fattura, String utenteUpdate) {
		this.log.info("FatturaService: saveFatturaConDettagli -> START");
		appService.insertLog("info", "FatturaService", "saveFatturaConDettagli", "START", "", "saveFatturaConDettagli");

		//inizializzo i models di ritorno
		com.gamenet.cruscottofatturazione.models.Fattura fatturaReturn= new com.gamenet.cruscottofatturazione.models.Fattura();
		List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> dettagliFatturaReturn = new ArrayList<>();
		Cliente clienteReturn= new Cliente();
		com.gamenet.cruscottofatturazione.entities.Cliente clienteSaved=clienteRepository.findByCodiceCliente(fattura.getCliente().getCodiceCliente());
		//inizializzo un oggetto Fattura entity
		Fattura fatturaSaved=new Fattura();
		try
		{	
			boolean isNew=false;
			//se sono in update 
			if(fattura.getId()!=null) {
				//recupero la fattura dal db
				fatturaSaved = fatturaRepository.findById(fattura.getId()).orElse(new Fattura());
				//ed elimino i dettagli vecchi
				if(fatturaSaved.getListaDettaglioFattura()!=null) {
					for (DettaglioFattura dettaglioFatturaSaved : fatturaSaved.getListaDettaglioFattura()) {
						dettaglioFatturaService.deleteDettaglioFattura(dettaglioFatturaSaved.getId(), utenteUpdate);
					}
				}
			}else
			{
				isNew=true;
			}
			fatturaSaved.setCliente(clienteSaved);
			//aggiorn0 i dati dell'entity con i dati del modello in input
			BeanUtils.copyProperties(fattura, fatturaSaved);
			if(fatturaSaved.getImporto()==null)
				fatturaSaved.setImporto(0.0);
			
			
//			if(isNew) //TODO:solo FE?
//				fatturaSaved.setStatoFattura(StatoFattura.IN_COMPILAZIONE.getKey());
			
			//inizializzo l'array dei dettagli qualora fosse una nuova fattura
			fatturaSaved.setListaDettaglioFattura(new HashSet<DettaglioFattura>());

			//salvo la testata della fattura per farmi restituire l'id da impostare nei dettagli

			fatturaSaved=fatturaRepository.save(fatturaSaved);
			if(isNew) {
				insertLogStatoFattura(fatturaSaved.getId(), utenteUpdate, StatoFattura.IN_COMPILAZIONE.getKey());
			}
			//ricalcolo progressivi e importo 
			int progressivo=1;
			Double importoFattura=0.0;
			//leggo i dettagli dal model
			for(com.gamenet.cruscottofatturazione.models.DettaglioFattura dettaglioFattura:fattura.getListaDettaglioFattura()) {

				//creo un entity per ogni dettaglio
				DettaglioFattura detFattura= new DettaglioFattura();
				//aggiorno i dati dal model
				BeanUtils.copyProperties(dettaglioFattura, detFattura);
				//setto l'id della fattura nel dettaglio
				detFattura.setIdFattura(fatturaSaved.getId());
				// per sicurezza resetto l'id
				detFattura.setId(null);
				detFattura.setProgressivoRiga(progressivo);
				detFattura.setLast_mod_date(new Date());
				detFattura.setLast_mod_user(utenteUpdate);
				detFattura.setCreate_date(new Date());
				detFattura.setCreate_user(utenteUpdate);
				//incremento l'importo della fattura
				importoFattura+=dettaglioFattura.getImporto();
				//salvo l'entity del dettaglio 
				detFattura=dettaglioFatturaService.saveDettaglioFattura(detFattura, utenteUpdate);

				//imposto il model del dettaglio di ritorno e l'aggiungo alla lista
				com.gamenet.cruscottofatturazione.models.DettaglioFattura dettaglioFatturaRet= new com.gamenet.cruscottofatturazione.models.DettaglioFattura();
				BeanUtils.copyProperties(detFattura, dettaglioFatturaRet);
				dettagliFatturaReturn.add(dettaglioFatturaRet);
				fatturaSaved.getListaDettaglioFattura().add(detFattura);
				progressivo++;
			}
			fatturaSaved.setImporto(importoFattura);
			fatturaSaved.setLast_mod_date(new Date());
			fatturaSaved.setLast_mod_user(utenteUpdate);
			// risalvo la fattura con l'importo corretto
			fatturaSaved=fatturaRepository.save(fatturaSaved);

			//imposto i model in risposta
			BeanUtils.copyProperties(fatturaSaved.getCliente(),clienteReturn);
			BeanUtils.copyProperties(fatturaSaved, fatturaReturn);
			fatturaReturn.setCliente(clienteReturn);
			fatturaReturn.setImporto((double) Math.round(importoFattura * 100) / 100);// fix per model
			fatturaReturn.setListaDettaglioFattura(dettagliFatturaReturn);//setto lista dettagli in model

		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FatturaService: saveFatturaConDettagli -> " + stackTrace);
			appService.insertLog("error", "FatturaService", "saveFatturaConDettagli", "Exception", stackTrace, "saveFatturaConDettagli");

			e.printStackTrace();
			return null;
		}

		this.log.info("FatturaService: saveFatturaConDettagli -> SUCCESSFULLY END");
		appService.insertLog("info", "FatturaService", "saveFatturaConDettagli", "SUCCESSFULLY END", "", "saveFatturaConDettagli");
		return fatturaReturn;
	}

	@Override
	public List<com.gamenet.cruscottofatturazione.models.Fattura> getLastTenFatturaBySocieta(String codiceSocieta) {
		List<Fattura> last10DayFattureBySocieta = fatturaRepository.getLast10DayFattureBySocieta(codiceSocieta);

		List<com.gamenet.cruscottofatturazione.models.Fattura> last10DayFattureBySocietaReturn= new ArrayList<>();

		for (Fattura fattura : last10DayFattureBySocieta) {
			com.gamenet.cruscottofatturazione.models.Fattura fattRet= new com.gamenet.cruscottofatturazione.models.Fattura();
			BeanUtils.copyProperties(fattura, fattRet);
			last10DayFattureBySocietaReturn.add(fattRet);

		}
		//		Comparator<Fattura> reverseComparator = (c1, c2) -> { 
		//	        return c1.getDataFattura().compareTo(c2.getDataFattura()); 
		//		};
		//		
		//		Collections.sort(last10DayFattureBySocieta, reverseComparator);
		return last10DayFattureBySocietaReturn;
	}

	/***** DATA TABLE LIST *****/
	@Override
	public FattureListOverview getFattureDataTable(JsonNode payload)
	{
		this.log.info("FattureService: getFattureDataTable -> START");
		appService.insertLog("info", "FattureService", "getFattureDataTable", "START", "", "getFattureDataTable");

		FattureListOverview response = new FattureListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<com.gamenet.cruscottofatturazione.models.Fattura>());

		try
		{
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String requestPrint = jsonMapper.writeValueAsString(payload);
				this.log.debug("FattureService: getUtentiDataTable -> Object: " + requestPrint);
				appService.insertLog("debug", "FattureService", "getFattureDataTable", "Object: " + requestPrint, "", "getFattureDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());

			Specification<com.gamenet.cruscottofatturazione.entities.Fattura> spec = new QuerySpecification<>();

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

			Page<com.gamenet.cruscottofatturazione.entities.Fattura> pages = fatturaRepository.findAll(spec, request);
			if (pages != null && pages.getTotalElements() > 0)
			{
				response.setTotalCount((int) pages.getTotalElements());

				for (com.gamenet.cruscottofatturazione.entities.Fattura ent_fatt : pages.getContent()) {
					com.gamenet.cruscottofatturazione.models.Fattura mod_fat = new com.gamenet.cruscottofatturazione.models.Fattura();
					com.gamenet.cruscottofatturazione.models.Cliente mod_cli = new com.gamenet.cruscottofatturazione.models.Cliente();
					BeanUtils.copyProperties(ent_fatt.getCliente(), mod_cli);
					BeanUtils.copyProperties(ent_fatt, mod_fat);
					mod_fat.setCliente(mod_cli);
					response.getLines().add(mod_fat);
				}		
			}

			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(response);
				this.log.debug("FattureService: getFattureDataTable -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "FattureService", "getFattureDataTable", "PROCESS END WITH: " + responsePrint, "", "getFattureDataTable");
			}
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: getFattureDataTable -> " + stackTrace);
			appService.insertLog("error", "FattureService", "getFattureDataTable", "Exception", stackTrace, "getFattureDataTable");

			throw new RuntimeException(e);
		}

		return response;
	}

	@Override
	public Boolean rifiutaFattura(Integer idFattura, String utenteUpdate) {
		try {


			this.log.info("FattureService: rifiutaFattura -> START");
			appService.insertLog("info", "FattureService", "rifiutaFattura", "START", "", "rifiutaFattura");

			Fattura fattura=fatturaRepository.findById(idFattura).orElse(null);
			if(fattura!=null) {

				fattura.setStatoFattura(StatoFattura.RIFIUTATA.getKey());
				fatturaRepository.save(fattura);
				insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.RIFIUTATA.getKey());
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: rifiutaFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "rifiutaFattura", "Exception", stackTrace, "rifiutaFattura");

			return false;
		}
	}

	@Override
	public Boolean inoltraFattura(Integer idFattura, String utenteUpdate) {
		try {


			this.log.info("FattureService: inoltraFattura -> START");
			appService.insertLog("info", "FattureService", "inoltraFattura", "START", "", "inoltraFattura");

			Fattura fattura=fatturaRepository.findById(idFattura).orElse(null);
			if(fattura!=null) {

				fattura.setStatoFattura(StatoFattura.DA_APPROVARE.getKey());
				fatturaRepository.save(fattura);
				insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.DA_APPROVARE.getKey());
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: inoltraFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "inoltraFattura", "Exception", stackTrace, "inoltraFattura");

			return false;
		}
	}

	@Override
	public Boolean validaFattura(Integer idFattura, String utenteUpdate) {
		try {


			this.log.info("FattureService: validaFattura -> START");
			appService.insertLog("info", "FattureService", "validaFattura", "START", "", "validaFattura");

			Fattura fattura=fatturaRepository.findById(idFattura).orElse(null);
			if(fattura!=null) {

				fattura.setStatoFattura(StatoFattura.VALIDATA.getKey());
				fatturaRepository.save(fattura);
				insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.VALIDATA.getKey());
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: validaFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "validaFattura", "Exception", stackTrace, "validaFattura");

			return false;
		}
	}

	private boolean insertLogStatoFattura(Integer idFattura,String utenteUpdate,String statofattura) {

		try {


			this.log.info("FattureService: insertLogStatoFattura -> START");
			appService.insertLog("info", "FattureService", "insertLogStatoFattura", "START", "", "insertLogStatoFattura");

			StatoFatturaLog statoFatturaLog = new StatoFatturaLog();
			statoFatturaLog.setCreateDate(new Date());
			statoFatturaLog.setCreateUser(utenteUpdate);
			statoFatturaLog.setIdFattura(idFattura);
			statoFatturaLog.setStatoFattura(statofattura);
			statoFatturaLogRepository.save(statoFatturaLog);
			return true;


		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: insertLogStatoFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "insertLogStatoFattura", "Exception", stackTrace, "insertLogStatoFattura");

			return false;
		}

	}

	@Override
	public List<StatoFatturaLog> getLogStatoFattura(Integer idFattura) {
		this.log.info("FatturaService: getLogStatoFattura -> START");
		appService.insertLog("info", "FatturaService", "getLogStatoFattura", "START", "", "getLogStatoFattura");
		List<StatoFatturaLog> result= new ArrayList<>();
		try {

			result=statoFatturaLogRepository.getStatofatturaLogByIdFattura(idFattura);

		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FatturaService: getLogStatoFattura -> " + stackTrace);
			appService.insertLog("error", "FatturaService", "getLogStatoFattura", "Exception", stackTrace, "getLogStatoFattura");

			e.printStackTrace();
			throw new RuntimeException(e);
		}

		this.log.info("FatturaService: getLogStatoFattura -> SUCCESSFULLY END");
		appService.insertLog("info", "FatturaService", "getLogStatoFattura", "SUCCESSFULLY END", "", "getLogStatoFattura");
		return result;
	}

}
