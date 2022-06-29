package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.VWDashboardLastWeek;
import com.gamenet.cruscottofatturazione.entities.VWDashboardMonth;
import com.gamenet.cruscottofatturazione.entities.VWDashboardTopSummary;
import com.gamenet.cruscottofatturazione.entities.VWDashboardYear;
import com.gamenet.cruscottofatturazione.models.DashboardLastWeek;
import com.gamenet.cruscottofatturazione.models.DashboardMonth;
import com.gamenet.cruscottofatturazione.models.DashboardSettimana;
import com.gamenet.cruscottofatturazione.models.DashboardTopSummary;
import com.gamenet.cruscottofatturazione.models.DashboardYear;
import com.gamenet.cruscottofatturazione.repositories.DashboardLastWeekRepository;
import com.gamenet.cruscottofatturazione.repositories.DashboardMonthRepository;
import com.gamenet.cruscottofatturazione.repositories.DashboardTopRepository;
import com.gamenet.cruscottofatturazione.repositories.DashboardYearRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.DashboardSummaryService;
import com.gamenet.cruscottofatturazione.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardSummaryServiceImpl implements DashboardSummaryService {

	private final DashboardTopRepository dashboardTopRepository;
	private final DashboardLastWeekRepository dashboardLastWeekRepository;
	private final DashboardYearRepository dashboardYearRepository;
	private final DashboardMonthRepository dashboardMonthRepository;

	private final ApplicationLogsService appService;

	private Logger log = LoggerFactory.getLogger(getClass());

	private ObjectMapper jsonMapper = new ObjectMapper();

	private final Environment env;
	private DateUtils dateUtils = new DateUtils();

	@Override
	public DashboardTopSummary getDashboardTopSummary(String codiceSocieta)
	{
		this.log.info("DashboardSummaryService: getDashboardTopSummary -> START");
		appService.insertLog("info", "DashboardSummaryService", "getDashboardTopSummary", "START", "", "getDashboardTopSummary");

		DashboardTopSummary result = new DashboardTopSummary();
		// START -> TOP LINE

		Iterable<VWDashboardTopSummary> dashList;


		dashList = dashboardTopRepository.getVWDashboardTopSummaryBySocieta(codiceSocieta);

		try
		{	
			Double importoTotale = 0.0;
			Integer fattureEmesse=0;
			for(VWDashboardTopSummary item: dashList)
			{

				fattureEmesse+=item.getNumero();
				importoTotale+=item.getImporto();//TODO
				//"V = validata,
				//R= rifiutata, 
				//blank “In compilazione”, -> I
				//“D” = da approvare, 
				//“C” = contabilizzata,
				//“G” = rigettata da SAP, 
				//“S” = validata da SAP."
				switch (item.getStatoFattura()) {
				case "R":

					result.setImportoFattureRifiutate(item.getImporto());
					result.setFattureRifiutate(item.getNumero());
					break;

				case "V":
					importoTotale+=item.getImporto();
					result.setFattureConvalidate(item.getNumero());
					break;	

				case "D":
					break;

				case "C":
					break;

				case "G":
					break;

				case "I":
					break;

				default:
					break;
				}


			}

			result.setTotaleImportoFatture(importoTotale);
			result.setDataImportoFatture(new Date());
			result.setDataFattureEmesse(new Date());
			result.setFattureEmesse(fattureEmesse);
			result.setDataFattureConvalidate(new Date());

			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(result);
				this.log.debug("DashboardSummaryService: getDashboardTopSummary -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "DashboardSummaryService", "getDashboardTopSummary", "PROCESS END WITH: " + responsePrint, "", "getDashboardTopSummary");
			}

		}
		catch(Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("DashboardSummaryService: getDashboardTopSummary -> " + stackTrace);
			appService.insertLog("error", "DashboardSummaryService", "getDashboardTopSummary", "Exception", stackTrace, "getDashboardTopSummary");

			throw new RuntimeException(e);
		}
		// END -> TOP LINE

		return result;
	}


	@Override
	public DashboardLastWeek getDashboardLastWeekChart(String codiceSocieta) {
		this.log.info("DashboardSummaryService: getDashboardLastWeekChart -> START");
		appService.insertLog("info", "DashboardSummaryService", "getDashboardLastWeekChart", "START", "", "getDashboardLastWeekChart");

		DashboardLastWeek result = new DashboardLastWeek();

		Iterable<VWDashboardLastWeek> dashList;


		dashList = dashboardLastWeekRepository.getVWDashboardLastWeekBySocieta(codiceSocieta);

		try
		{	
			for(VWDashboardLastWeek item: dashList)
			{

				switch (item.getGiornoSettimana()) {

				case "lunedì":
					result.setLunedi(item.getNumero());
					break;

				case "martedì":
					result.setMartedi(item.getNumero());
					break;

				case "mercoledì":
					result.setMercoledi(item.getNumero());
					break;

				case "giovedì":
					result.setGiovedi(item.getNumero());
					break;

				case "venerdì":
					result.setVenerdi(item.getNumero());
					break;

				case "sabato":
					result.setSabato(item.getNumero());
					break;

				case "domenica":
					result.setDomenica(item.getNumero());
					break;

				default:
					break;
				}


			}
			result.setLastUpdate(new Date());
			result.setIncrementoSettimana(null);//TODO


			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(result);
				this.log.debug("DashboardSummaryService: getDashboardLastWeekChart -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "DashboardSummaryService", "getDashboardLastWeekChart", "PROCESS END WITH: " + responsePrint, "", "getDashboardLastWeekChart");
			}

		}
		catch(Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("DashboardSummaryService: getDashboardLastWeekChart -> " + stackTrace);
			appService.insertLog("error", "DashboardSummaryService", "getDashboardLastWeekChart", "Exception", stackTrace, "getDashboardLastWeekChart");

			throw new RuntimeException(e);
		}
		// END -> TOP LINE

		return result;
	}


	@Override
	public DashboardYear getDashboardYearChart(String codiceSocieta) {
		this.log.info("DashboardSummaryService: getDashboardYearChart -> START");
		appService.insertLog("info", "DashboardSummaryService", "getDashboardYearChart", "START", "", "getDashboardYearChart");

		DashboardYear result = new DashboardYear();

		Iterable<VWDashboardYear> dashList;


		dashList = dashboardYearRepository.getVWDashboardYearBySocieta(codiceSocieta);

		try
		{	
			for(VWDashboardYear item: dashList)
			{

				switch (item.getNomeMese()) {

				case "gennaio":
					result.setGennaio(item.getNumero());
					break;

				case "febbraio":
					result.setFebbraio(item.getNumero());
					break;

				case "marzo":
					result.setMarzo(item.getNumero());
					break;

				case "aprile":
					result.setAprile(item.getNumero());
					break;

				case "maggio":
					result.setMaggio(item.getNumero());
					break;

				case "giugno":
					result.setGiugno(item.getNumero());
					break;

				case "luglio":
					result.setLuglio(item.getNumero());
					break;

				case "agosto":
					result.setAgosto(item.getNumero());
					break;

				case "settembre":
					result.setSettembre(item.getNumero());
					break;

				case "ottobre":
					result.setOttobre(item.getNumero());
					break;

				case "novembre":
					result.setNovembre(item.getNumero());
					break;

				case "dicembre":
					result.setDicembre(item.getNumero());
					break;

				default:
					break;
				}


			}
			result.setLastUpdate(new Date());


			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(result);
				this.log.debug("DashboardSummaryService: getDashboardYearChart -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "DashboardSummaryService", "getDashboardYearChart", "PROCESS END WITH: " + responsePrint, "", "getDashboardYearChart");
			}

		}
		catch(Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("DashboardSummaryService: getDashboardYearChart -> " + stackTrace);
			appService.insertLog("error", "DashboardSummaryService", "getDashboardYearChart", "Exception", stackTrace, "getDashboardYearChart");

			throw new RuntimeException(e);
		}
		// END -> TOP LINE

		return result;
	}


	@Override
	public DashboardMonth getDashboardMonthChart(String codiceSocieta,String stato) {
		this.log.info("DashboardSummaryService: getDashboardMonthChart -> START");
		appService.insertLog("info", "DashboardSummaryService", "getDashboardMonthChart", "START", "", "getDashboardMonthChart");

		DashboardMonth result = new DashboardMonth();

		Iterable<VWDashboardMonth> dashList;



		try
		{	
			dashList = dashboardMonthRepository.getVWDashboardMonthBySocietaAndStato(codiceSocieta,stato);
			Date date=new Date();
			Calendar c=Calendar.getInstance(Locale.ITALIAN);
			c.setTime(date);

			int numeroTotaliSettimaneMese= c.getActualMaximum(Calendar.WEEK_OF_MONTH);
			int primaSettimanaMese=dateUtils.getFirstWeekOfDate(date);

			
			LinkedHashMap<Integer, Integer> settimaneResult= new LinkedHashMap<>();

			//calcolo la lista delle settimane del mese
			int currentSettimana=primaSettimanaMese;
			for(int i=1 ; i<=numeroTotaliSettimaneMese ; i++ ) {
				settimaneResult.put(currentSettimana, 0);
				currentSettimana++;
			}


			for(VWDashboardMonth item: dashList)
			{
				settimaneResult.put(item.getSettimana(), item.getNumero());
			}

			//fix mismatch java-sqlserver
			if(settimaneResult.keySet().size()>numeroTotaliSettimaneMese) {
				settimaneResult.remove(primaSettimanaMese); //sicuro è vuota sqlserver potrebbe riportare una settimana avanti
			}
			
			
			LinkedList<DashboardSettimana> settimane= new LinkedList<>();

			int numeroSettimanaMese=1;
			for (Iterator iterator = settimaneResult.keySet().iterator(); iterator.hasNext();) {
				Integer settimana = (Integer) iterator.next();
				settimane.add(new DashboardSettimana(numeroSettimanaMese, settimaneResult.get(settimana)));
				numeroSettimanaMese++;
			}
			
			
			result.setSettimane(settimane);
			result.setLastUpdate(new Date());
			
			
			


			if(env.getProperty("cruscottofatturazione.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(result);
				this.log.debug("DashboardSummaryService: getDashboardMonthChart -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "DashboardSummaryService", "getDashboardMonthChart", "PROCESS END WITH: " + responsePrint, "", "getDashboardMonthChart");
			}

		}
		catch(Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("DashboardSummaryService: getDashboardYearChart -> " + stackTrace);
			appService.insertLog("error", "DashboardSummaryService", "getDashboardYearChart", "Exception", stackTrace, "getDashboardYearChart");

			throw new RuntimeException(e);
		}
		// END -> TOP LINE

		return result;
	}


}
