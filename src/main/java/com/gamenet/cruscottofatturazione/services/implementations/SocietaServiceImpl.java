package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.Societa;
import com.gamenet.cruscottofatturazione.repositories.SocietaRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.SocietaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocietaServiceImpl implements SocietaService
{
	
    private final SocietaRepository societaRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
    
	@Override
	public List<Societa> getSocieta() {
		return societaRepository.getSocieta();
	}

	@Override
	public Societa getSocietaById(Integer societaId) {
		return societaRepository.findById(societaId).orElse(null);
	}
	
	
	
	@Override
	public Boolean saveSocieta(Societa societa, String utenteUpdate) {
    	this.log.info("SocietaService: saveSocieta -> START");
    	appService.insertLog("info", "SocietaService", "saveSocieta", "START", "", "saveSocieta");

    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(societa);
		    	this.log.debug("ProspectService: saveSocieta -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveSocieta", "Object: " + requestPrint, "", "saveSocieta");
			}
        	
    		if(societa.getId()==null) {
    			societa.setCreate_date(new Date());
    			societa.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			societa.setLast_mod_user(utenteUpdate);
    			societa.setLast_mod_date(new Date());
    			
    		}
    		
    		societaRepository.save(societa);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("SocietaService: saveSocieta -> " + stackTrace);
			appService.insertLog("error", "SocietaService", "saveSocieta", "Exception", stackTrace, "saveSocieta");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("SocietaService: saveSocieta -> SUCCESSFULLY END");
    	appService.insertLog("info", "SocietaService", "saveSocieta", "SUCCESSFULLY END", "", "saveSocieta");
    	return true;
	}
}
