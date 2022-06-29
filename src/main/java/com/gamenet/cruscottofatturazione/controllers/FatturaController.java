package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.models.request.FatturaSaveRequest;
import com.gamenet.cruscottofatturazione.services.interfaces.FatturaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("fattura")
@CrossOrigin
@RequiredArgsConstructor
public class FatturaController {
	
	private final FatturaService fatturaService;
	
	
	@GetMapping("/getFattureList")
	public List<Fattura> getUserList() 
	{
		return fatturaService.getFatture();
	}
	
	@PostMapping("/getFatturaById")
	public Fattura getFatturaById(@RequestBody Integer fatturaId) 
	{
		return fatturaService.getFatturaById(fatturaId);
	}
	
	@GetMapping("/getLastTenFatturaBySocieta")
	public List<Fattura> getLastTenFatturaBySocieta(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return fatturaService.getLastTenFatturaBySocieta(codiceSocieta);
	}
	
	@PostMapping("/saveFattura")
	public Boolean saveUser(@RequestBody FatturaSaveRequest fatturaReq) 
	{
		return fatturaService.saveFattura(fatturaReq.getFattura(), fatturaReq.getUtenteUpdate());
	}
	
	
//	@PostMapping("/deleteFattura")
//	public Boolean deleteFattura(@RequestBody DeleteRequest fatturaReq) 
//	{
//		return fatturaService.deleteFattura(fatturaReq.getIdEntity(), fatturaReq.getUtenteUpdate());
//	}
	
	
}
