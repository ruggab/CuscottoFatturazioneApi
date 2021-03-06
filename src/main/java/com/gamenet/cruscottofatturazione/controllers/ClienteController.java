package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.request.ClienteSaveRequest;
import com.gamenet.cruscottofatturazione.models.request.DeleteClienteRequest;
import com.gamenet.cruscottofatturazione.models.response.ClienteAutoComplete;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;
import com.gamenet.cruscottofatturazione.models.response.SaveResponseCliente;
import com.gamenet.cruscottofatturazione.services.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cliente")
@CrossOrigin
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	
//	@GetMapping("/getClientiList")
//	public List<Cliente> getClientiList() 
//	{
//		return clienteService.getClienti();
//	}
	
	@GetMapping("/getClientiList")
	public List<Cliente> getClientiListBySocieta(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return clienteService.getClienti(codiceSocieta);
	}
	
	@GetMapping("/getClienti")
	public List<ClienteAutoComplete> getActiveClientiListBySocieta(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return clienteService.getActiveClienti(codiceSocieta);
	}
	
	@PostMapping("/getClienteByCodiceCliente")
	public Cliente getClienteById(@RequestBody String codiceCliente) 
	{
		return clienteService.getClienteById(codiceCliente);
	}
	
	@PostMapping("/saveCliente")
	public SaveResponseCliente saveCliente(@RequestBody ClienteSaveRequest clienteReq) 
	{
		return clienteService.saveCliente(clienteReq.getCliente(), clienteReq.getUtenteUpdate());
	}
	
	@PutMapping("/updateCliente")
	public SaveResponse updateCliente(@RequestBody ClienteSaveRequest clienteReq) 
	{
		return clienteService.updateCliente(clienteReq.getCliente(), clienteReq.getUtenteUpdate());
	}
	
	
	@PostMapping("/deleteCliente")
	public Boolean deleteCliente(@RequestBody DeleteClienteRequest clienteReq) 
	{
		return clienteService.deleteCliente(clienteReq.getCodiceCliente(), clienteReq.getUtenteUpdate());
	}
	
	
	/***** DATA TABLE LIST *****/
	@PostMapping("/getClientiDataTable")
	public ClientiListOverview getClientiDataTable(@RequestBody JsonNode payload,@PathParam(value ="codiceSocieta" ) String codiceSocieta)
	{
		return clienteService.getClientiDataTable(payload);
	}
	
}
