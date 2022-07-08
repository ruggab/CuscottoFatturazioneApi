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
import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.models.ListFilter;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.User;
import com.gamenet.cruscottofatturazione.models.response.ArticoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.UtentiListOverview;
import com.gamenet.cruscottofatturazione.repositories.ArticoloRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.ArticoloService;
import com.gamenet.cruscottofatturazione.services.interfaces.DettaglioFatturaService;
import com.gamenet.cruscottofatturazione.services.interfaces.FatturaService;
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
	private final DettaglioFatturaService dettaglioFatturaService;

	@Override
	public List<Articolo> getArticoli(Boolean soloAttivi) {

		List<Articolo> articoli = new ArrayList<>();
		if(soloAttivi)
			articoli =articoloRepository.getActiveArticoli();
		else
			articoli=articoloRepository.getArticoli();

		return  articoli ;
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
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
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
				Integer countDettagliFatturaArticolo = dettaglioFatturaService.getCountDettaglioFatturaByArticoloValidateSap(articolo.getCodiceArticolo());

				if(countDettagliFatturaArticolo==0) {
					articolo.setDataValidita(new Date());
					articolo.setLast_mod_user(utenteUpdate);
					articolo.setLast_mod_date(new Date());
					articoloRepository.save(articolo);
				}
				else
					return false;
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



	/***** DATA TABLE LIST *****/
	@Override
	public ArticoliListOverview getArticoliDataTable(JsonNode payload)
	{
		this.log.info("ArticoloService: getArticoliDataTable -> START");
		appService.insertLog("info", "ArticoloService", "getArticoliDataTable", "START", "", "getArticoliDataTable");

		ArticoliListOverview response = new ArticoliListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<com.gamenet.cruscottofatturazione.models.Articolo>());

		try
		{
			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String requestPrint = jsonMapper.writeValueAsString(payload);
				this.log.debug("ArticoloService: getUtentiDataTable -> Object: " + requestPrint);
				appService.insertLog("debug", "ArticoloService", "getArticoliDataTable", "Object: " + requestPrint, "", "getArticoliDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());

			Specification<com.gamenet.cruscottofatturazione.entities.Articolo> spec = new QuerySpecification<>();

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

			Page<com.gamenet.cruscottofatturazione.entities.Articolo> pages = articoloRepository.findAll(spec, request);
			if (pages != null && pages.getTotalElements() > 0)
			{
				response.setTotalCount((int) pages.getTotalElements());

				for (com.gamenet.cruscottofatturazione.entities.Articolo ent_articolo : pages.getContent()) {
					com.gamenet.cruscottofatturazione.models.Articolo mod_articolo = new com.gamenet.cruscottofatturazione.models.Articolo();
					BeanUtils.copyProperties(ent_articolo, mod_articolo);

					response.getLines().add(mod_articolo);
				}		
			}

			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(response);
				this.log.debug("ArticoloService: getArticoliDataTable -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "ArticoloService", "getArticoliDataTable", "PROCESS END WITH: " + responsePrint, "", "getArticoliDataTable");
			}
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("ArticoloService: getArticoliDataTable -> " + stackTrace);
			appService.insertLog("error", "ArticoloService", "getArticoliDataTable", "Exception", stackTrace, "getArticoliDataTable");

			throw new RuntimeException(e);
		}

		return response;
	}

}
