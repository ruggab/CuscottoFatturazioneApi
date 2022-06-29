package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.repositories.FatturaRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
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
	
	@Override
	public List<Fattura> getFatture() {
		return fatturaRepository.getFatture();
	}

	@Override
	public Fattura getFatturaById(Integer fatturaId) {
		return fatturaRepository.findById(fatturaId).orElse(null);
	}

	
	@Override
	public Boolean saveFattura(Fattura fattura, String utenteUpdate) {
    	this.log.info("FatturaService: saveFattura -> START");
    	appService.insertLog("info", "FatturaService", "saveFattura", "START", "", "saveFattura");

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
	public List<Fattura> getLastTenFatturaBySocieta(String codiceSocieta) {
		List<Fattura> last10DayFattureBySocieta = fatturaRepository.getLast10DayFattureBySocieta(codiceSocieta);
		
//		Comparator<Fattura> reverseComparator = (c1, c2) -> { 
//	        return c1.getDataFattura().compareTo(c2.getDataFattura()); 
//		};
//		
//		Collections.sort(last10DayFattureBySocieta, reverseComparator);
		return last10DayFattureBySocieta;
	}
	
}
