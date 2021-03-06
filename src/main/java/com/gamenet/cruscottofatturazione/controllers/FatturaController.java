package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.StatoFatturaLog;
import com.gamenet.cruscottofatturazione.models.request.FatturaActionRequest;
import com.gamenet.cruscottofatturazione.models.request.FatturaSaveRequest;
import com.gamenet.cruscottofatturazione.models.response.FattureListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;
import com.gamenet.cruscottofatturazione.models.response.SaveResponseFattura;
import com.gamenet.cruscottofatturazione.services.interfaces.FatturaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("fattura")
@CrossOrigin
@RequiredArgsConstructor
public class FatturaController {
	
	private final FatturaService fatturaService;
	
	
//	@GetMapping("/getFattureList")
//	public List<Fattura> geFattureList() String codiceSocieta) 
//	{
//		return fatturaService.getFatture();
//	}
	
	@GetMapping("/getFattureList")
	public List<com.gamenet.cruscottofatturazione.models.Fattura> geFattureList(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return fatturaService.getFatture(codiceSocieta);
	}
	
	@PostMapping("/getFatturaById")
	public com.gamenet.cruscottofatturazione.models.Fattura getFatturaById(@RequestBody Integer fatturaId) 
	{
		
		com.gamenet.cruscottofatturazione.models.Fattura fattura=fatturaService.getFatturaById(fatturaId);
		
		return fattura;
	}
	
	@GetMapping("/getLastTenFatturaBySocieta")
	public List<com.gamenet.cruscottofatturazione.models.Fattura> getLastTenFatturaBySocieta(@PathParam(value ="codiceSocieta" ) String codiceSocieta) 
	{
		return fatturaService.getLastTenFatturaBySocieta(codiceSocieta);
	}
	
	@PostMapping("/saveFattura")
	public SaveResponseFattura saveFattura(@RequestBody FatturaSaveRequest fatturaReq) 
	{
		return fatturaService.saveFatturaConDettagli(fatturaReq.getFattura(), fatturaReq.getUtenteUpdate());
	}
	
	@PostMapping("/rifiutaFattura")
	public Boolean rifiutaFattura(@RequestBody FatturaActionRequest fatturaActionReq) 
	{
		return fatturaService.rifiutaFattura(fatturaActionReq.getIdFattura(), fatturaActionReq.getUtenteUpdate());
	}
	
	@PostMapping("/validaFattura")
	public SaveResponse validaFattura(@RequestBody FatturaActionRequest fatturaActionReq) 
	{
		return fatturaService.validaFattura(fatturaActionReq.getIdFattura(), fatturaActionReq.getUtenteUpdate());
	}
	
	@PostMapping("/inoltraFattura")
	public SaveResponse inoltraFattura(@RequestBody FatturaActionRequest fatturaActionReq) 
	{
		return fatturaService.inoltraFattura(fatturaActionReq.getIdFattura(), fatturaActionReq.getUtenteUpdate());
	}
	
	@GetMapping("/getLogStatoFattura")
	public List<StatoFatturaLog> geFattureList(@PathParam(value ="idFattura" ) Integer idFattura) 
	{
		return fatturaService.getLogStatoFattura(idFattura);
	}
	
	
	/***** DATA TABLE LIST *****/
	@PostMapping("/getFattureDataTable")
	public FattureListOverview getFattureDataTable(@RequestBody JsonNode payload,@PathParam(value ="codiceSocieta" ) String codiceSocieta)
	{
		return fatturaService.getFattureDataTable(payload,codiceSocieta);
	}
	
//	@PostMapping("/deleteFattura")
//	public Boolean deleteFattura(@RequestBody DeleteRequest fatturaReq) 
//	{
//		return fatturaService.deleteFattura(fatturaReq.getIdEntity(), fatturaReq.getUtenteUpdate());
//	}
	
	
}
