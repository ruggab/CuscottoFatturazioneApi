package com.gamenet.cruscottofatturazione.services.implementations;

import java.beans.Beans;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.Enum.EsitoSap;
import com.gamenet.cruscottofatturazione.Enum.StatoFattura;
import com.gamenet.cruscottofatturazione.context.QuerySpecification;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.StatoFatturaLog;
import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.entities.User;
import com.gamenet.cruscottofatturazione.models.Cliente;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.response.FattureListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;
import com.gamenet.cruscottofatturazione.models.response.SaveResponseFattura;
import com.gamenet.cruscottofatturazione.models.sap.request.DatiContabiliRequest;
import com.gamenet.cruscottofatturazione.models.sap.request.IT_CFA_INPUT;
import com.gamenet.cruscottofatturazione.models.sap.request.Item;
import com.gamenet.cruscottofatturazione.models.sap.response.DatiContabiliResponse;
import com.gamenet.cruscottofatturazione.repositories.ArticoloRepository;
import com.gamenet.cruscottofatturazione.repositories.ClienteRepository;
import com.gamenet.cruscottofatturazione.repositories.FatturaRepository;
import com.gamenet.cruscottofatturazione.repositories.RoleRepository;
import com.gamenet.cruscottofatturazione.repositories.StatoFatturaLogRepository;
import com.gamenet.cruscottofatturazione.repositories.TipologiaCorrispettiviRepository;
import com.gamenet.cruscottofatturazione.repositories.UserRepository;
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
	private final UserRepository userRepository;
	private final ArticoloRepository articoloRepository;
	private final TipologiaCorrispettiviRepository tipologiaCorrispettiviRepository;
	private Logger log = LoggerFactory.getLogger(getClass());
	private final ApplicationLogsService appService;
	private final Environment env;
	private ObjectMapper jsonMapper = new ObjectMapper();

	@Value("${sap.url.fatture.service}")
    private String urlFatturaServiceSap;
    private final RestTemplate restTemplate;
	
	private final DettaglioFatturaService dettaglioFatturaService;

	@Override
	public List<Fattura> getFatture() {
		return fatturaRepository.getFatture();
	}

	@Override
	public List<com.gamenet.cruscottofatturazione.models.Fattura> getFatture(String societa) {
		boolean isAprrovatore=getCurrentUser().getRuoloUtente().getName().equals("Approvatore");
		List<Fattura> fatture = new ArrayList<>();
		if(isAprrovatore)
			fatture = fatturaRepository.getFattureBySocietaAndStato(societa,StatoFattura.DA_APPROVARE.getKey());
		else
			fatture = fatturaRepository.getFattureBySocieta(societa);

		
		List<com.gamenet.cruscottofatturazione.models.Fattura> fattureRet= new ArrayList<>();
		for (Fattura fattura : fatture) {
			com.gamenet.cruscottofatturazione.models.Fattura fatRet= new com.gamenet.cruscottofatturazione.models.Fattura();
			Cliente cliente = new Cliente();
			BeanUtils.copyProperties(fattura, fatRet);
			BeanUtils.copyProperties(fattura.getCliente(), cliente);
			fatRet.setCliente(cliente);
			fattureRet.add(fatRet);
		}

		return fattureRet;
	}


	@Override
	public  com.gamenet.cruscottofatturazione.models.Fattura getFatturaById(Integer fatturaId) {
		
		Fattura fattura = fatturaRepository.findById(fatturaId).orElse(null);
				
		com.gamenet.cruscottofatturazione.models.Fattura fatturaModel= new com.gamenet.cruscottofatturazione.models.Fattura();
		Cliente clienteReturn= new Cliente();
		if(fattura!=null) {
			
			BeanUtils.copyProperties(fattura, fatturaModel);
			BeanUtils.copyProperties(fattura.getCliente(),clienteReturn);
			//dettaglioFattura
			List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> dettaglioFatturaModelList=new ArrayList<>();
			
			
			for (DettaglioFattura dettaglioFattura : fattura.getListaDettaglioFattura()) {
				com.gamenet.cruscottofatturazione.models.DettaglioFattura dettaglioFatturaModel = new com.gamenet.cruscottofatturazione.models.DettaglioFattura();
				Articolo articolo = articoloRepository.getArticoloByCodice(dettaglioFattura.getCodiceArticolo());
				TipologiaCorrispettivi corrispettivo=tipologiaCorrispettiviRepository.getTipologiaByCodice(dettaglioFattura.getCodiceCorrispettivo());
				BeanUtils.copyProperties(dettaglioFattura, dettaglioFatturaModel);
				if(articolo!=null)
					dettaglioFatturaModel.setDescrizioneArticolo(articolo.getDescrizione());
				if(corrispettivo!=null)
					dettaglioFatturaModel.setDescrizioneCorrispettivo(corrispettivo.getDescrizione());
				dettaglioFatturaModelList.add(dettaglioFatturaModel);
				
			}
			fatturaModel.setCliente(clienteReturn);
			fatturaModel.setListaDettaglioFattura(dettaglioFatturaModelList);
		}
		
		return fatturaModel;
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
	public SaveResponseFattura saveFatturaConDettagli(com.gamenet.cruscottofatturazione.models.Fattura fattura, String utenteUpdate) {
		this.log.info("FatturaService: saveFatturaConDettagli -> START");
		appService.insertLog("info", "FatturaService", "saveFatturaConDettagli", "START", "", "saveFatturaConDettagli");

		SaveResponseFattura saveResponse = new SaveResponseFattura();
		
		//inizializzo i models di ritorno
		com.gamenet.cruscottofatturazione.models.Fattura fatturaReturn= new com.gamenet.cruscottofatturazione.models.Fattura();
		List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> dettagliFatturaReturn = new ArrayList<>();
		Cliente clienteReturn= new Cliente();
		com.gamenet.cruscottofatturazione.entities.Cliente clienteSaved=clienteRepository.findByCodiceCliente(fattura.getCliente().getCodiceCliente());
		//inizializzo un oggetto Fattura entity
		Fattura fatturaSaved=new Fattura();
		
		
		try
		{	
			
			
			//check dettagli - importi <=0
			String erroreImportoDettagliFattura=checkImportiDettaglio(fattura.getListaDettaglioFattura());
			if(!erroreImportoDettagliFattura.equals("")) {
				throw new Exception(erroreImportoDettagliFattura);
			}
			
			//check dettagli - articoli corrispettivi
			String erroreDettagliFattura=checkArticoliCorrispettivi(fattura.getListaDettaglioFattura());
			if(!erroreDettagliFattura.equals("")) {
				throw new Exception(erroreDettagliFattura);
			}
			
			
			//check articoli non validi
			String erroreArticoloNonValidoDettagliFattura=checkArticoliNonValidiDettaglio(fattura.getListaDettaglioFattura());
			if(!erroreArticoloNonValidoDettagliFattura.equals("")) {
				throw new Exception(erroreArticoloNonValidoDettagliFattura);
			}
			
			boolean isNew=false;
			//se sono in update 
			if(fattura.getId()!=null) {
				//recupero la fattura dal db
				fatturaSaved = fatturaRepository.findById(fattura.getId()).orElse(new Fattura());
				
				if(!(fatturaSaved.getStatoFattura().equals(StatoFattura.IN_COMPILAZIONE.getKey()) || fatturaSaved.getStatoFattura().equals(StatoFattura.RIFIUTATA.getKey())) )
					throw new Exception("la fattura con id: "+fattura.getId()+ " non puo essere modificata perche non è nello stato "+StatoFattura.IN_COMPILAZIONE.getValue()+" o "+StatoFattura.RIFIUTATA.getValue());
				
				//ed elimino i dettagli vecchi
				if(fatturaSaved.getListaDettaglioFattura()!=null) {
					for (DettaglioFattura dettaglioFatturaSaved : fatturaSaved.getListaDettaglioFattura()) {
						dettaglioFatturaService.deleteDettaglioFattura(dettaglioFatturaSaved.getId(), utenteUpdate);
					}
				}
			}else
			{
				isNew=true;
				if(fattura.getImporto()==null) // nel caso non mi venga passato l'importo
					fattura.setImporto(0.0);
			}
			fatturaSaved.setCliente(clienteSaved);
			//aggiorn0 i dati dell'entity con i dati del modello in input
			BeanUtils.copyProperties(fattura, fatturaSaved);
			
			
			
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
				if (dettaglioFattura.getImporto() == null) {
					dettaglioFattura.setImporto(0D);
				}
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
				
				Articolo articolo = articoloRepository.getArticoloByCodice(dettaglioFattura.getCodiceArticolo());
				TipologiaCorrispettivi corrispettivo=tipologiaCorrispettiviRepository.getTipologiaByCodice(dettaglioFattura.getCodiceCorrispettivo());

				if(articolo!=null)
					dettaglioFatturaRet.setDescrizioneArticolo(articolo.getDescrizione());
				if(corrispettivo!=null)
					dettaglioFatturaRet.setDescrizioneCorrispettivo(corrispettivo.getDescrizione());
				
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
			
			saveResponse.setErrore(e.getMessage());
			saveResponse.setEsito(false);
			saveResponse.setFattura(null);
			return saveResponse;
		}

		this.log.info("FatturaService: saveFatturaConDettagli -> SUCCESSFULLY END");
		appService.insertLog("info", "FatturaService", "saveFatturaConDettagli", "SUCCESSFULLY END", "", "saveFatturaConDettagli");
		saveResponse.setErrore(null);
		saveResponse.setEsito(true);
		saveResponse.setFattura(fatturaReturn);
		
		
		return saveResponse;
	}

	private String checkArticoliNonValidiDettaglio(	List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> listaDettaglioFattura) {
		String errore="";
		
		 List<String> codiciArticoli = listaDettaglioFattura.stream().map(com.gamenet.cruscottofatturazione.models.DettaglioFattura::getCodiceArticolo).collect(Collectors.toList());
		
		List<String> articoliNonValidi= articoloRepository.findArticoliNonValidi(codiciArticoli);
		 
		 if(!articoliNonValidi.isEmpty()) {
			 errore="Attenzione sono stati inseriti i seguenti articoli non piu validi: ";
			 errore+=String.join(", ", articoliNonValidi);
			 errore+=" -  Si prega di rimuoverli prima di continuare";
		 }
		 
		 return errore;
	}

	private String checkImportiDettaglio(List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> listaDettaglioFattura) {
		String errore="";
		
		 List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> dettagliImportiNonValidi = listaDettaglioFattura.stream().filter(d->d.getImporto()<=0).collect(Collectors.toList());
		
		 if(!dettagliImportiNonValidi.isEmpty()) {
			 errore="Attenzione sono stati inseriti importi negativi o uguali a 0";
		 }
		 
		 return errore;
	}

	private String checkArticoliCorrispettivi(List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> listaDettaglioFattura) {
		//int maxSizeCorrispettivi=2;
		String errore="";
		LinkedHashMap<String, List<String>> articoloCorrispettivoMap = new  LinkedHashMap<String, List<String>>();
		LinkedHashMap<String, String> articoloCorrispettivoDuplicateMap = new  LinkedHashMap<String, String>();

		
		
		//check doppia coppia articolo-corrispettivo
		for (com.gamenet.cruscottofatturazione.models.DettaglioFattura dettaglioFattura : listaDettaglioFattura) {
			if(articoloCorrispettivoMap.containsKey(dettaglioFattura.getCodiceArticolo()) && articoloCorrispettivoMap.get(dettaglioFattura.getCodiceArticolo()).contains(dettaglioFattura.getCodiceCorrispettivo())) {
				articoloCorrispettivoDuplicateMap.put(dettaglioFattura.getCodiceArticolo(), dettaglioFattura.getCodiceCorrispettivo());
			}
			else {
				List<String> corrispettivi = new ArrayList<>();
				if(articoloCorrispettivoMap.containsKey(dettaglioFattura.getCodiceArticolo()))
					corrispettivi=articoloCorrispettivoMap.get(dettaglioFattura.getCodiceArticolo());

				corrispettivi.add(dettaglioFattura.getCodiceCorrispettivo());
				articoloCorrispettivoMap.put(dettaglioFattura.getCodiceArticolo(),corrispettivi);
			}

		}
		
		
		if(!articoloCorrispettivoDuplicateMap.isEmpty()) {
			errore+="Attenzione, per i seguenti articoli è stato inserito lo stesso corrispettivo: ";
			errore+= String.join(", ", articoloCorrispettivoDuplicateMap.keySet());
		}
		
		//check piu corrispettivi per articolo
//		List<String> articoliOverCorrispettivi = new ArrayList<>();
//		if(!articoloCorrispettivoMap.isEmpty()) {
//			for( String articolo : articoloCorrispettivoMap.keySet()) {
//				if(articoloCorrispettivoMap.get(articolo).size()>maxSizeCorrispettivi)
//					articoliOverCorrispettivi.add(articolo);
//
//			}
//		}
//		
//		if(articoliOverCorrispettivi.size()>0) {
//			errore+="<br> Attenzione Per i seguenti codici articolo sono stati inseriti piu' di "+maxSizeCorrispettivi +" codici corrispettivo previsti :<br>";
//			errore+= String.join(", ", articoliOverCorrispettivi);
//		}


		return errore;
	}

	@Override
	public List<com.gamenet.cruscottofatturazione.models.Fattura> getLastTenFatturaBySocieta(String codiceSocieta) {
		List<Fattura> last10DayFattureBySocieta = new ArrayList<>();
		
		boolean isAprrovatore=getCurrentUser().getRuoloUtente().getName().equals("Approvatore");
		if(isAprrovatore)
			last10DayFattureBySocieta = fatturaRepository.getLast10DayFattureBySocieta(codiceSocieta,StatoFattura.DA_APPROVARE.getKey());
		else
			last10DayFattureBySocieta = fatturaRepository.getLast10DayFattureBySocieta(codiceSocieta);
		

		List<com.gamenet.cruscottofatturazione.models.Fattura> last10DayFattureBySocietaReturn= new ArrayList<>();

		for (Fattura fattura : last10DayFattureBySocieta) {
			com.gamenet.cruscottofatturazione.models.Fattura fattRet= new com.gamenet.cruscottofatturazione.models.Fattura();
			Cliente cliente = new Cliente();
			BeanUtils.copyProperties(fattura, fattRet);
			BeanUtils.copyProperties(fattura.getCliente(), cliente);
			fattRet.setCliente(cliente);
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
	public FattureListOverview getFattureDataTable(JsonNode payload,String codiceSocieta)
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

			
			
			boolean isAprrovatore=getCurrentUser().getRuoloUtente().getName().equals("Approvatore");
			
			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());
			
			
			//filtro societa
			ListFilter filtroSocieta= new ListFilter();
			filtroSocieta.setName("societa");
			filtroSocieta.setOperator("eq");
			filtroSocieta.setValue(codiceSocieta);
			model.getFilters().add(filtroSocieta);
			

			//ordina per data fattura desc di default:
			ListSort sortDataFattura = model.getSort().stream().filter(f -> f.getName().equals("dataFattura"))
					.findAny()
					.orElse(null);
			
			if(sortDataFattura==null) {
				sortDataFattura= new ListSort();
				sortDataFattura.setName("dataFattura");
				sortDataFattura.setType("DESC");
				model.getSort().add(sortDataFattura);
			}
			
			if(isAprrovatore) {
				ListFilter soloDaApprovareFilter = model.getFilters().stream().filter(f -> f.getName().equals("statoFattura"))
						.findAny()
						.orElse(null);

				if(soloDaApprovareFilter!=null) {
					model.getFilters().remove(soloDaApprovareFilter);
				}
				ListFilter soloDaApprovare= new ListFilter();
				soloDaApprovare.setName("statoFattura");
				soloDaApprovare.setOperator("eq");
				soloDaApprovare.setValue(StatoFattura.DA_APPROVARE.getKey());
				model.getFilters().add(soloDaApprovare);
			}


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

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userdetails= (UserDetails) authentication.getPrincipal();
		return userRepository.findByUsername(userdetails.getUsername());
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
	public SaveResponse inoltraFattura(Integer idFattura, String utenteUpdate) {
		SaveResponse saveResponse = new SaveResponse();
		try {
			saveResponse.setEsito(true);

			this.log.info("FattureService: inoltraFattura -> START");
			appService.insertLog("info", "FattureService", "inoltraFattura", "START", "", "inoltraFattura");

			Fattura fattura=fatturaRepository.findById(idFattura).orElse(null);
			if(fattura!=null) {

				String erroreArticoliNonValidi=checkArticoliNonValidiDettaglio(getDettagliFatturaModel(fattura));
				if(!erroreArticoliNonValidi.equals(""))
					throw new Exception(erroreArticoliNonValidi);
				
				fattura.setStatoFattura(StatoFattura.DA_APPROVARE.getKey());
				fatturaRepository.save(fattura);
				insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.DA_APPROVARE.getKey());
				saveResponse.setEsito(true);
			}
			else {
				
				saveResponse.setEsito(false);
				saveResponse.setErrore("fattura non trovata");
				
			}
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: inoltraFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "inoltraFattura", "Exception", stackTrace, "inoltraFattura");

			saveResponse.setEsito(false);
			saveResponse.setErrore(e.getMessage());
		}
		return saveResponse;
	}

	@Override
	public SaveResponse validaFattura(Integer idFattura, String utenteUpdate) {
		SaveResponse saveResponse = new SaveResponse();
		try {

			saveResponse.setEsito(true);

			this.log.info("FattureService: validaFattura -> START");
			appService.insertLog("info", "FattureService", "validaFattura", "START", "", "validaFattura");

			Fattura fattura=fatturaRepository.findById(idFattura).orElse(null);
			if(fattura!=null) {


				
				String erroreArticoliNonValidi=checkArticoliNonValidiDettaglio(getDettagliFatturaModel(fattura));
				if(!erroreArticoliNonValidi.equals(""))
					throw new Exception(erroreArticoliNonValidi);

				if(!fattura.getStatoFattura().equals(StatoFattura.DA_APPROVARE.getKey()))
					throw new Exception("la fattura con id: "+fattura.getId()+ " non puo essere approvata perche non è nello stato "+StatoFattura.DA_APPROVARE.getValue());

				fattura.setStatoFattura(StatoFattura.VALIDATA.getKey());
				fattura=fatturaRepository.save(fattura);
				insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.VALIDATA.getKey());
				
				//sap
				
				if (validaFatturaSap(fattura))
				{
					fattura.setStatoFattura(StatoFattura.VALIDATA_DA_SAP.getKey());
					fattura.setDataInvioFlusso(new Date());
					fattura=fatturaRepository.save(fattura);
					insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.VALIDATA_DA_SAP.getKey());
				}else {
					fattura.setStatoFattura(StatoFattura.RIGETTATA_DA_SAP.getKey());
					fattura.setDataInvioFlusso(new Date());
					fattura=fatturaRepository.save(fattura);
					insertLogStatoFattura(fattura.getId(),utenteUpdate,StatoFattura.RIGETTATA_DA_SAP.getKey());
				}
					
				
				saveResponse.setEsito(true);
			}
			else {

				saveResponse.setEsito(false);
				saveResponse.setErrore("fattura non trovata");
			}
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FattureService: validaFattura -> " + stackTrace);
			appService.insertLog("error", "FattureService", "validaFattura", "Exception", stackTrace, "validaFattura");
			saveResponse.setEsito(false);
			saveResponse.setErrore(e.getMessage());
		}
		
		return saveResponse;
	}

	private boolean validaFatturaSap(Fattura fattura) {
		this.log.info("FatturaService: validaFatturaSap -> START");
    	appService.insertLog("info", "FatturaService", "validaFatturaSap", "START", "", "validaFatturaSap");
		Boolean esito=true;
		try {
			
			SimpleDateFormat simpleDateFormatFattura= new SimpleDateFormat("yyyyMMdd");
			String dataFlusso=simpleDateFormatFattura.format(new Date());
			DatiContabiliRequest datiContabiliRequest = new DatiContabiliRequest();
			
			IT_CFA_INPUT IT_CFA_INPUT = new IT_CFA_INPUT();
			IT_CFA_INPUT.setItem(new ArrayList<>());
			
			
			
			List<DettaglioFattura> dettaglioFatturaList =new ArrayList<>(fattura.getListaDettaglioFattura());
			
			for (DettaglioFattura dettaglioFattura : dettaglioFatturaList) {
				Item item = new Item();
				item.setDATA_FLUSSO(dataFlusso);
				item.setARTICOLO(dettaglioFattura.getCodiceArticolo());
				item.setBUKRS(fattura.getSocieta());
				item.setID_FATTURA(fattura.getId().toString());
				item.setIMP_IMPONIBILE(dettaglioFattura.getImporto().toString());
				item.setKUNNR(fattura.getCliente().getCodiceCliente());
				item.setTIPO_CORRISPETTIVO(dettaglioFattura.getCodiceCorrispettivo());
				item.setTIPO_OPERAZIONE(fattura.getTipologiaFattura());
				IT_CFA_INPUT.getItem().add(item);
			}
			
			datiContabiliRequest.setIT_CFA_INPUT(IT_CFA_INPUT);
			
			ResponseEntity<DatiContabiliResponse> response = restTemplate.postForEntity(urlFatturaServiceSap, datiContabiliRequest, DatiContabiliResponse.class);
			this.log.info("ResponseCode: "+response .getStatusCode().toString());
			if(response.getStatusCode().equals(HttpStatus.OK)) {

				this.log.info("FatturaService: validaFatturaSap -> esito: " + response.getBody().getES_RETURN().getESITO() +" - idMessaggio: " + response.getBody().getES_RETURN().getIDMESSAGGIO()+ " - message: "+response.getBody().getES_RETURN().getDESCRIZIONE());
				
				
				if (!response.getBody().getES_RETURN().getESITO().equals(EsitoSap.SUCCESSO.getValue())){
					esito=false;
				}
				
			}
			else {
				throw new Exception("errore durante l'invocazione dei servizi SAP: "+response.getStatusCode().toString());
			}
		
		
		} catch (Throwable e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("FatturaService: validaFatturaSap -> " + stackTrace);
			appService.insertLog("error", "FatturaService", "validaFatturaSap", "Exception", stackTrace, "validaFatturaSap");
			esito= false;
		}
		
		this.log.info("FatturaService: validaFatturaSap -> SUCCESSFULLY END");
    	appService.insertLog("info", "FatturaService", "validaFatturaSap", "SUCCESSFULLY END", "", "validaFatturaSap");
		
		return esito;
	}

	private List<com.gamenet.cruscottofatturazione.models.DettaglioFattura>  getDettagliFatturaModel(Fattura fattura) {
		List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> listaDettaglioFattura= new ArrayList<>();
		for (DettaglioFattura dettaglioFattura : fattura.getListaDettaglioFattura()) {
			com.gamenet.cruscottofatturazione.models.DettaglioFattura detFattura= new com.gamenet.cruscottofatturazione.models.DettaglioFattura();
			BeanUtils.copyProperties(dettaglioFattura, detFattura);
			listaDettaglioFattura.add(detFattura);
		}
		return listaDettaglioFattura;
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
