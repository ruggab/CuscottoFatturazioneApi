package com.gamenet.cruscottofatturazione.services.interfaces;

public interface ApplicationLogsService {

	public Boolean insertLog(String type, String area, String method, String message, String stackTrace, String utenteUpdate);

}
