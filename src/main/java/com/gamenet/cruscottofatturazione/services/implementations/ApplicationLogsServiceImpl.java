package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.gamenet.cruscottofatturazione.entities.ApplicationLog;
import com.gamenet.cruscottofatturazione.repositories.ApplicationLogsRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationLogsServiceImpl implements ApplicationLogsService {
	
	private final ApplicationLogsRepository logRepo;
    
    private final Environment env;
    
    private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
    public Boolean insertLog(String type, String area, String method, String message, String stackTrace, String utenteUpdate)
    {
		// 1 - DEBUG
		// 2 - INFO
		// 3 - ERROR
		String logLevel = env.getProperty("database.log.level");
		Boolean writeLog = false;
		
		if(logLevel.equals("debug"))
		{
			switch (type)
			{
				case "debug":
				case "info":
				case "error":
					writeLog = true;
					break;
				default:
					writeLog = false;
					break;
			}
		}
		else if(logLevel.equals("info"))
		{
			switch (type)
			{
				case "info":
				case "error":
					writeLog = true;
					break;
				default:
					writeLog = false;
					break;
			}
		}
		else if(logLevel.equals("error"))
		{
			switch (type)
			{
				case "error":
					writeLog = true;
					break;
				default:
					writeLog = false;
					break;
			}
		}
		
		if(writeLog)
		{
			try
			{
				ApplicationLog appLog = new ApplicationLog();
				appLog.setType(type);
				appLog.setArea(area);
				appLog.setMethod(method);
				appLog.setMessage(message);
				appLog.setStackTrace(stackTrace);
				appLog.setCreateUser(utenteUpdate);
				appLog.setCreateDate(new Date());
				
				logRepo.save(appLog);
				return true;
			}
			catch (Exception e) {
	    		String stackTraceExc = ExceptionUtils.getStackTrace(e);
	    		this.log.error("ApplicationLogsService: insertLog -> " + stackTraceExc);
	    		return false;
			}
		}
		else
		{
			return false;
		}
    }

}
