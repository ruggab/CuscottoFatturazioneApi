package com.gamenet.cruscottofatturazione.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public Date getCurrentDateWithoutTime() {
		 Calendar now = Calendar.getInstance();
	        now.set(Calendar.HOUR, 0);
	        now.set(Calendar.HOUR_OF_DAY, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        now.set(Calendar.MILLISECOND, 0);
	        return now.getTime();
	}
	
	public Integer getFirstWeekOfDate(Date date) {
		 Calendar c = Calendar.getInstance();
		 	c.setTime(date);
	        c.set(Calendar.DAY_OF_MONTH, 1);
	        return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	
	String[] giorniSettimana = {"domenica","lunedi", "martedi", "mercoledi", "giovedi","venerdi","sabato"};
	
	public String getDayNameOfDate(Date date) {
		 Calendar c = Calendar.getInstance();
		 	c.setTime(date);
		 	int giornoSettimanaNumero= c.get(Calendar.DAY_OF_WEEK);
		 	
		 	return giorniSettimana[giornoSettimanaNumero-1];
	}
	
}
