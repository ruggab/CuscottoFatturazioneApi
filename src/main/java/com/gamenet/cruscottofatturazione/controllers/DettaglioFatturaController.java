package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.models.request.DeleteRequest;
import com.gamenet.cruscottofatturazione.models.request.DettaglioFatturaSaveRequest;
import com.gamenet.cruscottofatturazione.services.interfaces.DettaglioFatturaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("dettaglioFattura")
@CrossOrigin
@RequiredArgsConstructor
public class DettaglioFatturaController {
	
	private final DettaglioFatturaService dettaglioFatturaService;
	
	
	@GetMapping("/getDettaglioFattureList")
	public List<DettaglioFattura> getDettaglioFattureList() 
	{
		return dettaglioFatturaService.getDettaglioFatture();
	}
	
	@PostMapping("/getDettaglioFatturaById")
	public DettaglioFattura getFatturaById(@RequestBody Integer dettaglioFatturaId) 
	{
		return dettaglioFatturaService.getDettaglioFatturaById(dettaglioFatturaId);
	}
	
	@PostMapping("/getDettaglioFatturaByFatturaId")
	public List<DettaglioFattura> getDettaglioFatturaByFatturaId(@RequestBody Integer FatturaId) 
	{
		return dettaglioFatturaService.getDettaglioFatturaByFatturaId(FatturaId);
	}
	
	@PostMapping("/saveDettaglioFattura")
	public Boolean saveDettaglioFattura(@RequestBody DettaglioFatturaSaveRequest dettaglioFatturaReq) 
	{
		DettaglioFattura saveDettaglioFattura = dettaglioFatturaService.saveDettaglioFattura(dettaglioFatturaReq.getDettaglioFattura(), dettaglioFatturaReq.getUtenteUpdate());
		return (saveDettaglioFattura!=null && saveDettaglioFattura.getId()!=null);
	}
	
	
	@PostMapping("/deleteDettaglioFattura")
	public Boolean deleteFattura(@RequestBody DeleteRequest dettaglioFatturaReq) 
	{
		return dettaglioFatturaService.deleteDettaglioFattura(dettaglioFatturaReq.getIdEntity(), dettaglioFatturaReq.getUtenteUpdate());
	}
	
	
}
