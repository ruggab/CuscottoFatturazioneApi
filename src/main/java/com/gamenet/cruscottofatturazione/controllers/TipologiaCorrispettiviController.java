package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.models.request.DeleteRequest;
import com.gamenet.cruscottofatturazione.models.request.TipologiaCorrispettiviSaveRequest;
import com.gamenet.cruscottofatturazione.services.interfaces.TipologiaCorrispettiviService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tipologiaCorrispettivi")
@CrossOrigin
@RequiredArgsConstructor
public class TipologiaCorrispettiviController {
	
	private final TipologiaCorrispettiviService tipologiaCorrispettiviService;
	
	
	@GetMapping("/getTipologiaCorrispettiviList")
	public List<TipologiaCorrispettivi> getUserList() 
	{
		return tipologiaCorrispettiviService.getTipologiaCorrispettivi();
	}
	
	@PostMapping("/getTipologiaCorrispettiviById")
	public TipologiaCorrispettivi getTipologiaCorrispettiviById(@RequestBody Integer tipologiaCorrispettiviId) 
	{
		return tipologiaCorrispettiviService.getTipologiaCorrispettiviById(tipologiaCorrispettiviId);
	}
	
	@PostMapping("/saveTipologiaCorrispettivi")
	public Boolean saveTipologiaCorrispettivo(@RequestBody TipologiaCorrispettiviSaveRequest clienteReq) 
	{
		return tipologiaCorrispettiviService.saveTipologiaCorrispettivi(clienteReq.getTipologiaCorrispettivi(), clienteReq.getUtenteUpdate());
	}

	@PostMapping("/deleteTipologiaCorrispettivi")
	public Boolean deleteTipologiaCorrispettivi(@RequestBody DeleteRequest tipologiaCorrispettiviReq) 
	{
		return tipologiaCorrispettiviService.deleteTipologiaCorrispettivi(tipologiaCorrispettiviReq.getIdEntity(), tipologiaCorrispettiviReq.getUtenteUpdate());
	}
	
}
