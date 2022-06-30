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
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.repositories.FatturaRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.DettaglioFatturaService;
import com.gamenet.cruscottofatturazione.services.interfaces.FatturaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FatturaServiceImpl implements FatturaService
{

	private final FatturaRepository fatturaRepository;
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
	public Fattura saveFattura(Fattura fattura, String utenteUpdate) {
		this.log.info("FatturaService: saveFattura -> START");
		appService.insertLog("info", "FatturaService", "saveFattura", "START", "", "saveFattura");
		Fattura fatturaSaved=null;
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

			fatturaSaved=fatturaRepository.save(fattura);

		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("FatturaService: saveFattura -> " + stackTrace);
			appService.insertLog("error", "FatturaService", "saveFattura", "Exception", stackTrace, "saveFattura");

			e.printStackTrace();
			return null;
		}

		this.log.info("FatturaService: saveFattura -> SUCCESSFULLY END");
		appService.insertLog("info", "FatturaService", "saveFattura", "SUCCESSFULLY END", "", "saveFattura");
		return fatturaSaved;
	}

	@Override
	public com.gamenet.cruscottofatturazione.models.Fattura saveFatturaConDettagli(com.gamenet.cruscottofatturazione.models.Fattura fattura, String utenteUpdate) {
		this.log.info("FatturaService: saveFatturaConDettagli -> START");
		appService.insertLog("info", "FatturaService", "saveFatturaConDettagli", "START", "", "saveFatturaConDettagli");

		//inizializzo i models di ritorno
		com.gamenet.cruscottofatturazione.models.Fattura fatturaReturn= new com.gamenet.cruscottofatturazione.models.Fattura();
		List<com.gamenet.cruscottofatturazione.models.DettaglioFattura> dettagliFatturaReturn = new ArrayList<>();
	
		//inizializzo un oggetto Fattura entity
		Fattura fatturaSaved=new Fattura();
		try
		{	
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
			}

			//aggiorn0 i dati dell'entity con i dati del modello in input
			BeanUtils.copyProperties(fattura, fatturaSaved);
			//inizializzo l'array dei dettagli qualora fosse una nuova fattura
			fatturaSaved.setListaDettaglioFattura(new HashSet<DettaglioFattura>());

			//salvo la testata della fattura per farmi restituire l'id da impostare nei dettagli
			fatturaSaved=fatturaRepository.save(fatturaSaved);

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
			BeanUtils.copyProperties(fatturaSaved, fatturaReturn);
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

}
