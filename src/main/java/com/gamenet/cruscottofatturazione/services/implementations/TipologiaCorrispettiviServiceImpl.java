package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.ArrayList;
import java.util.Date;
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
import com.gamenet.cruscottofatturazione.context.QuerySpecification;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.response.ArticoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.TipologiaCorrispettiviListOverview;
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
	public List<TipologiaCorrispettivi> getTipologiaCorrispettivi(Boolean soloAttivi) {
		
		List<TipologiaCorrispettivi> corrispettivi = new ArrayList<TipologiaCorrispettivi>();
		if(soloAttivi)
			corrispettivi=tipologiaCorrispettiviRepository.getActiveTipologiaCorrispettivi();
		else
			corrispettivi=tipologiaCorrispettiviRepository.getTipologiaCorrispettivi();
		
		return corrispettivi;
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
    			 tipologiaCorrispettivi.setDataValidita(new Date());
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
	
	
	/***** DATA TABLE LIST *****/
    @Override
    public TipologiaCorrispettiviListOverview getTipologiaCorrispettiviDataTable(JsonNode payload)
    {
    	this.log.info("TipologiaCorrispettiviService: getTipologiaCorrispettiviDataTable -> START");
    	appService.insertLog("info", "TipologiaCorrispettiviService", "getTipologiaCorrispettiviDataTable", "START", "", "getTipologiaCorrispettiviDataTable");

    	TipologiaCorrispettiviListOverview response = new TipologiaCorrispettiviListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<com.gamenet.cruscottofatturazione.models.TipologiaCorrispettivi>());
		
		try
		{
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(payload);
		    	this.log.debug("TipologiaCorrispettiviService: getUtentiDataTable -> Object: " + requestPrint);
		    	appService.insertLog("debug", "TipologiaCorrispettiviService", "getTipologiaCorrispettiviDataTable", "Object: " + requestPrint, "", "getTipologiaCorrispettiviDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());
			
			Specification<com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi> spec = new QuerySpecification<>();

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

			Page<com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi> pages = tipologiaCorrispettiviRepository.findAll(spec, request);
			if (pages != null && pages.getTotalElements() > 0)
			{
				response.setTotalCount((int) pages.getTotalElements());
				
				for (com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi ent_tip : pages.getContent()) {
					com.gamenet.cruscottofatturazione.models.TipologiaCorrispettivi mod_tip = new com.gamenet.cruscottofatturazione.models.TipologiaCorrispettivi();
					BeanUtils.copyProperties(ent_tip, mod_tip);
					
					response.getLines().add(mod_tip);
				}		
			}
	    	
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(response);
		    	this.log.debug("TipologiaCorrispettiviService: getTipologiaCorrispettiviDataTable -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "TipologiaCorrispettiviService", "getTipologiaCorrispettiviDataTable", "PROCESS END WITH: " + responsePrint, "", "getTipologiaCorrispettiviDataTable");
			}
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("TipologiaCorrispettiviService: getTipologiaCorrispettiviDataTable -> " + stackTrace);
			appService.insertLog("error", "TipologiaCorrispettiviService", "getTipologiaCorrispettiviDataTable", "Exception", stackTrace, "getTipologiaCorrispettiviDataTable");
			
			throw new RuntimeException(e);
		}
   
		return response;
    }
	
	
	
	
	
	
}
