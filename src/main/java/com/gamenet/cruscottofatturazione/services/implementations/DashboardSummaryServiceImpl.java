package com.gamenet.cruscottofatturazione.services.implementations;

import java.text.SimpleDateFormat;
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
import com.gamenet.cruscottofatturazione.models.DashboardDay;
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
			Integer totaleFatture=0;
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

					totaleFatture+=item.getNumero();
					result.setFattureRifiutate(item.getNumero());
					break;

				case "V":
					importoTotale+=item.getImporto();
					result.setFattureConvalidate(item.getNumero());
					totaleFatture+=item.getNumero();
					break;	

				case "D":
					break;

				case "C":
					break;

				case "G":
					break;

				case "I":
					break;
					
				case "S":
					totaleFatture+=item.getNumero();
					break;

				default:
					break;
				}


			}

			result.setTotaleImportoFatture((double) Math.round(importoTotale * 100) / 100);
			result.setDataImportoFatture(new Date());
			result.setDataFattureEmesse(new Date());
			result.setFattureEmesse(fattureEmesse);
			result.setDataFattureConvalidate(new Date());
			result.setTotaleFatture(totaleFatture);
			
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

		try
		{	
			Iterable<VWDashboardLastWeek> dashList;
			dashList = dashboardLastWeekRepository.getVWDashboardBySocieta(codiceSocieta, 14);

			LinkedList<DashboardDay> giorni= new LinkedList<>();


			Date date = new Date();
			Calendar c= Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, -13);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			LinkedHashMap<String, Integer> ultimi14giorni = new LinkedHashMap<>();

			for(int i=0 ; i<14; i++) {
				String currentDate=dateFormat.format(c.getTime());
				ultimi14giorni.put(currentDate, 0);
				c.add(Calendar.DAY_OF_MONTH, 1);

			}


			Integer sumPrevWeek=0;
			Integer sumCurrentWeek=0;

			for(VWDashboardLastWeek item: dashList)
			{
				ultimi14giorni.put(item.getGiorno(),item.getNumero());
			}
			int count=1;
			for (Iterator iterator = ultimi14giorni.keySet().iterator(); iterator.hasNext();) {

				String day = (String) iterator.next();
				if(count<=7) {
					sumPrevWeek+=ultimi14giorni.get(day);
				}
				else {

					Date currDate = dateFormat.parse(day);
					giorni.add(new DashboardDay(day,ultimi14giorni.get(day),dateUtils.getDayNameOfDate(currDate)));
					sumCurrentWeek+=ultimi14giorni.get(day);
				}
				count++;

			}

			result.setGiorni(giorni);
			result.setLastUpdate(new Date());
			result.setIncrementoSettimana(percent(sumPrevWeek,sumCurrentWeek));

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

//
//		public static void main(String[] args) {
//			DashboardSummaryServiceImpl dashboardSummaryServiceImpl= new DashboardSummaryServiceImpl(null, null, null, null, null, null);
//			
//			int a=1;
//			int b=10000;
//			System.out.println(dashboardSummaryServiceImpl.percent(a, b));
//		}
		
	
	
	private double percent(double a, double b)
	{
		double result = 0;

		if(a==b) {
			result=0;
			return result;
		}
		if(a==0) {
			result=100.0;
			return result;
		}
		
		
		if(a!=0 || b!=0){
			result =  (((b - a) * 100) / a);
			result = Math.round(result * 100);
			result = result/100;
		}
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
	
//	public static void main(String[] args) {
//		Calendar cal = Calendar.getInstance();
//        for(int i = 0 ; i < 11;i++){
//            cal.set(Calendar.YEAR, 2022);
//            cal.set(Calendar.DAY_OF_MONTH, 1);
//            cal.set(Calendar.MONTH, i);
//            int maxWeeknumber = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
//            // Month value starts from 0 to 11 for Jan to Dec
//           System.out.println("For Month : "+ i + " Num Week :: " + maxWeeknumber); 
//        }
//	}


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
			int primaSettimanaMese=dateUtils.getFirstWeekOfDate(c.getTime());


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
