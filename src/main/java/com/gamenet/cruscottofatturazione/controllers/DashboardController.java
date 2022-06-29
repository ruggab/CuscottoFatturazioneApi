package com.gamenet.cruscottofatturazione.controllers;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.models.DashboardLastWeek;
import com.gamenet.cruscottofatturazione.models.DashboardMonth;
import com.gamenet.cruscottofatturazione.models.DashboardTopSummary;
import com.gamenet.cruscottofatturazione.models.DashboardYear;
import com.gamenet.cruscottofatturazione.services.interfaces.DashboardSummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardController {
	
	private final DashboardSummaryService dashService;
	
	@GetMapping("/getDashboardTopSummary")
	public DashboardTopSummary getDashboardTopSummary(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return dashService.getDashboardTopSummary(codiceSocieta);
	}
	
	@GetMapping("/getDashboardLastWeekChart")
	public DashboardLastWeek getDashboardLastWeekChart(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return dashService.getDashboardLastWeekChart(codiceSocieta);
	}
	
	@GetMapping("/getDashboardYearChart")
	public DashboardYear getDashboardYearChart(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return dashService.getDashboardYearChart(codiceSocieta);
	}
	
	@GetMapping("/getDashboardMonthChart")
	public DashboardMonth getDashboardMonthChart(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return dashService.getDashboardMonthChart(codiceSocieta,"R");
	}
	
	

}
