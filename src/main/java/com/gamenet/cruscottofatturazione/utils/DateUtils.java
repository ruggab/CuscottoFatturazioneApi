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
	
}
