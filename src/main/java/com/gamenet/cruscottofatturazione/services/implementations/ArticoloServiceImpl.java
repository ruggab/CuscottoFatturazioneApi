package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.repositories.ArticoloRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.ArticoloService;
import com.gamenet.cruscottofatturazione.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticoloServiceImpl implements ArticoloService
{
	
    private final ArticoloRepository articoloRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
	private DateUtils dateUtils = new DateUtils();
	@Override
	public List<Articolo> getArticoli() {
		return articoloRepository.getArticoli();
	}

	@Override
	public Articolo getArticoloById(Integer articoloId) {
		return articoloRepository.findById(articoloId).orElse(null);
	}
	
	
	@Override
	public Boolean saveArticolo(Articolo articolo, String utenteUpdate) {
    	this.log.info("ArticoloService: saveArticolo -> START");
    	appService.insertLog("info", "ArticoloService", "saveArticolo", "START", "", "saveArticolo");

    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(articolo);
		    	this.log.debug("ProspectService: saveArticolo -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveArticolo", "Object: " + requestPrint, "", "saveArticolo");
			}
        	
    		if(articolo.getId()==null) {
    			articolo.setCreate_date(new Date());
    			articolo.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			articolo.setLast_mod_user(utenteUpdate);
    			articolo.setLast_mod_date(new Date());
    			
    		}
    		
    		articoloRepository.save(articolo);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ArticoloService: saveArticolo -> " + stackTrace);
			appService.insertLog("error", "ArticoloService", "saveArticolo", "Exception", stackTrace, "saveArticolo");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ArticoloService: saveArticolo -> SUCCESSFULLY END");
    	appService.insertLog("info", "ArticoloService", "saveArticolo", "SUCCESSFULLY END", "", "saveArticolo");
    	return true;
	}

	
	@Override
	public Boolean deleteArticolo(Integer articoloId, String utenteUpdate) {
		this.log.info("ArticoloService: deleteArticolo -> [articoloId: " + articoloId.toString() + "]");
    	appService.insertLog("info", "ArticoloService", "deleteArticolo", "[articoloId: " + articoloId.toString() + "]", "", "deleteArticolo");
    	
    	try
		{	
    		 Articolo articolo= articoloRepository.findById(articoloId).orElse(null);
    		 
    		 if(articolo!=null) {
    			 //TODO: verificare se ci sono fatture con Articolo
    			 articolo.setDataValidita(dateUtils.getCurrentDateWithoutTime());
    			 articolo.setLast_mod_user(utenteUpdate);
    			 articolo.setLast_mod_date(new Date());
    			 articoloRepository.save(articolo);
    		 }
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ArticoloService: deleteArticolo -> " + stackTrace);
			appService.insertLog("error", "ArticoloService", "deleteArticolo", "Exception", stackTrace, "deleteArticolo");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ArticoloService: deleteArticolo -> SUCCESSFULLY END");
    	appService.insertLog("info", "ArticoloService", "deleteArticolo", "SUCCESSFULLY END", "", "deleteArticolo");
    	return true;
	}
	
}
