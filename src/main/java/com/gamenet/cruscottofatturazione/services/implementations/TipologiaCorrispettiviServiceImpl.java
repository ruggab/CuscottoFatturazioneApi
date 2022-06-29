package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.repositories.TipologiaCorrispettiviRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.TipologiaCorrispettiviService;
import com.gamenet.cruscottofatturazione.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipologiaCorrispettiviServiceImpl implements TipologiaCorrispettiviService
{
	
    private final TipologiaCorrispettiviRepository tipologiaCorrispettiviRepository;
	
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private DateUtils dateUtils = new DateUtils();
    
	@Override
	public List<TipologiaCorrispettivi> getTipologiaCorrispettivi() {
		return tipologiaCorrispettiviRepository.getTipologiaCorrispettivi();
	}

	@Override
	public TipologiaCorrispettivi getTipologiaCorrispettiviById(Integer tipologiaCorrispettiviId) {
		return tipologiaCorrispettiviRepository.findById(tipologiaCorrispettiviId).orElse(null);
	}
	
	@Override
	public Boolean saveTipologiaCorrispettivi(TipologiaCorrispettivi tipologiaCorrispettivi, String utenteUpdate) {
    	this.log.info("TipologiaCorrispettiviService: saveTipologiaCorrispettivi -> START");
    	appService.insertLog("info", "TipologiaCorrispettiviService", "saveTipologiaCorrispettivi", "START", "", "saveTipologiaCorrispettivi");

    	try
		{	
    		if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(tipologiaCorrispettivi);
		    	this.log.debug("ProspectService: saveTipologiaCorrispettivi -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveTipologiaCorrispettivi", "Object: " + requestPrint, "", "saveTipologiaCorrispettivi");
			}
        	
    		if(tipologiaCorrispettivi.getId()==null) {
    			tipologiaCorrispettivi.setCreate_date(new Date());
    			tipologiaCorrispettivi.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			tipologiaCorrispettivi.setLast_mod_user(utenteUpdate);
    			tipologiaCorrispettivi.setLast_mod_date(new Date());
    			
    		}
    		
    		tipologiaCorrispettiviRepository.save(tipologiaCorrispettivi);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("TipologiaCorrispettiviService: saveTipologiaCorrispettivi -> " + stackTrace);
			appService.insertLog("error", "TipologiaCorrispettiviService", "saveTipologiaCorrispettivi", "Exception", stackTrace, "saveTipologiaCorrispettivi");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("TipologiaCorrispettiviService: saveTipologiaCorrispettivi -> SUCCESSFULLY END");
    	appService.insertLog("info", "TipologiaCorrispettiviService", "saveTipologiaCorrispettivi", "SUCCESSFULLY END", "", "saveTipologiaCorrispettivi");
    	return true;
	}
	
	
	@Override
	public Boolean deleteTipologiaCorrispettivi(Integer tipologiaCorrispettiviId, String utenteUpdate) {
		this.log.info("TipologiaCorrispettiviService: deleteTipologiaCorrispettivi -> [tipologiaCorrispettiviId: " + tipologiaCorrispettiviId.toString() + "]");
    	appService.insertLog("info", "TipologiaCorrispettiviService", "deleteTipologiaCorrispettivi", "[tipologiaCorrispettiviId: " + tipologiaCorrispettiviId.toString() + "]", "", "deleteTipologiaCorrispettivi");
    	
    	try
		{	
    		 TipologiaCorrispettivi tipologiaCorrispettivi= tipologiaCorrispettiviRepository.findById(tipologiaCorrispettiviId).orElse(null);
    		 
    		 if(tipologiaCorrispettivi!=null) {
    			 //TODO: verificare se ci sono fatture con TipologiaCorrispettivi????
    			 tipologiaCorrispettivi.setDataValidita(dateUtils.getCurrentDateWithoutTime());
    			 tipologiaCorrispettivi.setLast_mod_user(utenteUpdate);
    			 tipologiaCorrispettivi.setLast_mod_date(new Date());
    			 tipologiaCorrispettiviRepository.save(tipologiaCorrispettivi);
    		 }
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("TipologiaCorrispettiviService: deleteTipologiaCorrispettivi -> " + stackTrace);
			appService.insertLog("error", "TipologiaCorrispettiviService", "deleteTipologiaCorrispettivi", "Exception", stackTrace, "deleteTipologiaCorrispettivi");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("TipologiaCorrispettiviService: deleteTipologiaCorrispettivi -> SUCCESSFULLY END");
    	appService.insertLog("info", "TipologiaCorrispettiviService", "deleteTipologiaCorrispettivi", "SUCCESSFULLY END", "", "deleteTipologiaCorrispettivi");
    	return true;
	}
	
	
	
	
	
	
	
	
	
}
