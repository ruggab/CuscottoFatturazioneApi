package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.repositories.DettaglioFatturaRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.DettaglioFatturaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DettaglioFatturaServiceImpl implements DettaglioFatturaService
{
	
    private final DettaglioFatturaRepository dettaglioFatturaRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public List<DettaglioFattura> getDettaglioFatture() {
		return dettaglioFatturaRepository.getDettagliFatture();
	}

	@Override
	public DettaglioFattura getDettaglioFatturaById(Integer dettaglioFatturaId) {
		return dettaglioFatturaRepository.findById(dettaglioFatturaId).orElse(null);
	}

	@Override
	public  List<DettaglioFattura> getDettaglioFatturaByFatturaId(Integer fatturaId) {
		return dettaglioFatturaRepository.findByIdFattura(fatturaId);
	}

	@Override
	public Boolean saveDettaglioFattura(DettaglioFattura dettaglioFattura, String utenteUpdate) {
		this.log.info("DettaglioFatturaService: saveDettaglioFattura -> START");
    	appService.insertLog("info", "DettaglioFatturaService", "saveDettaglioFattura", "START", "", "saveDettaglioFattura");

    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(dettaglioFattura);
		    	this.log.debug("ProspectService: saveDettaglioFattura -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveDettaglioFattura", "Object: " + requestPrint, "", "saveDettaglioFattura");
			}
        	
    		if(dettaglioFattura.getId()==null) {
    			dettaglioFattura.setCreate_date(new Date());
    			dettaglioFattura.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			dettaglioFattura.setLast_mod_user(utenteUpdate);
    			dettaglioFattura.setLast_mod_date(new Date());
    			
    		}
    		
    		dettaglioFatturaRepository.save(dettaglioFattura);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("DettaglioFatturaService: saveDettaglioFattura -> " + stackTrace);
			appService.insertLog("error", "DettaglioFatturaService", "saveDettaglioFattura", "Exception", stackTrace, "saveDettaglioFattura");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("DettaglioFatturaService: saveDettaglioFattura -> SUCCESSFULLY END");
    	appService.insertLog("info", "DettaglioFatturaService", "saveDettaglioFattura", "SUCCESSFULLY END", "", "saveDettaglioFattura");
    	return true;
	}

	@Override
	public Boolean deleteDettaglioFattura(Integer dettaglioFatturaId, String utenteUpdate) {
		this.log.info("DettaglioFatturaService: deleteDettaglioFattura -> [dettaglioFatturaId: " + dettaglioFatturaId.toString() + "]");
    	appService.insertLog("info", "DettaglioFatturaService", "deleteDettaglioFattura", "[dettaglioFatturaId: " + dettaglioFatturaId.toString() + "]", "", "deleteDettaglioFattura");
    	
    	try
		{	
    		 DettaglioFattura dettaglioFattura= dettaglioFatturaRepository.findById(dettaglioFatturaId).orElse(null);
    		 
    		 if(dettaglioFattura!=null) {
    			 dettaglioFatturaRepository.delete(dettaglioFattura);
    		 }
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("DettaglioFatturaService: deleteDettaglioFattura -> " + stackTrace);
			appService.insertLog("error", "DettaglioFatturaService", "deleteDettaglioFattura", "Exception", stackTrace, "deleteDettaglioFattura");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("DettaglioFatturaService: deleteDettaglioFattura -> SUCCESSFULLY END");
    	appService.insertLog("info", "DettaglioFatturaService", "deleteDettaglioFattura", "SUCCESSFULLY END", "", "deleteDettaglioFattura");
    	return true;
	}
		
}
