package com.gamenet.cruscottofatturazione.services.interfaces;

import com.gamenet.cruscottofatturazione.models.DashboardLastWeek;
import com.gamenet.cruscottofatturazione.models.DashboardMonth;
import com.gamenet.cruscottofatturazione.models.DashboardTopSummary;
import com.gamenet.cruscottofatturazione.models.DashboardYear;

public interface DashboardSummaryService {
	public DashboardTopSummary getDashboardTopSummary(String codiceSocieta);
	public DashboardLastWeek getDashboardLastWeekChart(String codiceSocieta);
	public DashboardYear getDashboardYearChart(String codiceSocieta);
	public DashboardMonth getDashboardMonthChart(String codiceSocieta, String stato);

	
}
