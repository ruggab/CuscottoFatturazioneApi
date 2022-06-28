package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.request.ClienteSaveRequest;
import com.gamenet.cruscottofatturazione.models.request.DeleteRequest;
import com.gamenet.cruscottofatturazione.services.interfaces.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cliente")
@CrossOrigin
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	
	@GetMapping("/getClientiList")
	public List<Cliente> getUserList() 
	{
		return clienteService.getClienti();
	}
	
	@PostMapping("/getClienteById")
	public Cliente getUserById(@RequestBody Integer clienteId) 
	{
		return clienteService.getClienteById(clienteId);
	}
	
	@PostMapping("/saveCliente")
	public Boolean saveUser(@RequestBody ClienteSaveRequest clienteReq) 
	{
		return clienteService.saveCliente(clienteReq.getCliente(), clienteReq.getUtenteUpdate());
	}
	
	
	@PostMapping("/deleteCliente")
	public Boolean deleteUser(@RequestBody DeleteRequest clienteReq) 
	{
		return clienteService.deleteCliente(clienteReq.getIdEntity(), clienteReq.getUtenteUpdate());
	}
	
	
}
