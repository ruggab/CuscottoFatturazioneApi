package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.repositories.ClienteRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService
{
	
    private final ClienteRepository clienteRepository;
    private Logger log = LoggerFactory.getLogger(getClass());
    private final ApplicationLogsService appService;
    private final Environment env;
    private ObjectMapper jsonMapper = new ObjectMapper();
    
	@Override
	public List<Cliente> getClienti() {
		return clienteRepository.getClienti();
	}

	@Override
	public Cliente getClienteById(Integer clienteId) {
		return clienteRepository.findById(clienteId).orElse(null);
	}

	@Override
	public Boolean saveCliente(Cliente cliente, String utenteUpdate) {
    	this.log.info("ClienteService: saveCliente -> START");
    	appService.insertLog("info", "ClienteService", "saveCliente", "START", "", "saveCliente");

    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(cliente);
		    	this.log.debug("ProspectService: saveCliente -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveCliente", "Object: " + requestPrint, "", "saveCliente");
			}
        	
    		if(cliente.getId()==null) {
    			cliente.setCreate_date(new Date());
    			cliente.setCreate_user(utenteUpdate);
    		}
    		else
    		{
    			cliente.setLast_mod_user(utenteUpdate);
    			cliente.setLast_mod_date(new Date());
    			
    		}
    		
    		clienteRepository.save(cliente);
    		
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: saveCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "saveCliente", "Exception", stackTrace, "saveCliente");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ClienteService: saveCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "saveCliente", "SUCCESSFULLY END", "", "saveCliente");
    	return true;
	}

	@Override
	public Boolean deleteCliente(Integer clienteId, String utenteUpdate) {
		this.log.info("ClienteService: deleteCliente -> [clienteId: " + clienteId.toString() + "]");
    	appService.insertLog("info", "ClienteService", "deleteCliente", "[clienteId: " + clienteId.toString() + "]", "", "deleteCliente");
    	
    	try
		{	
    		 Cliente cliente= clienteRepository.findById(clienteId).orElse(null);
    		 
    		 if(cliente!=null) {
    			 //TODO: verificare se ci sono fatture con cliente
    			// clienteRepository.deleteCliente(clienteId);
    			 clienteRepository.delete(cliente);
    		 }
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("ClienteService: deleteCliente -> " + stackTrace);
			appService.insertLog("error", "ClienteService", "deleteCliente", "Exception", stackTrace, "deleteCliente");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("ClienteService: deleteCliente -> SUCCESSFULLY END");
    	appService.insertLog("info", "ClienteService", "deleteCliente", "SUCCESSFULLY END", "", "deleteCliente");
    	return true;
	}
	
}
