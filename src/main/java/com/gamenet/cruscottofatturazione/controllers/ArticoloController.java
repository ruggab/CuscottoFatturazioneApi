package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.models.request.ArticoloSaveRequest;
import com.gamenet.cruscottofatturazione.models.request.DeleteRequest;
import com.gamenet.cruscottofatturazione.models.response.ArticoliListOverview;
import com.gamenet.cruscottofatturazione.services.interfaces.ArticoloService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("articolo")
@CrossOrigin
@RequiredArgsConstructor
public class ArticoloController {
	
	private final ArticoloService articoloService;
	
	
	@GetMapping("/getArticoliList")
	public List<Articolo> getUserList() 
	{
		return articoloService.getArticoli();
	}
	
	@PostMapping("/getArticoloById")
	public Articolo getArticoloById(@RequestBody Integer articoloId) 
	{
		return articoloService.getArticoloById(articoloId);
	}
	
	@PostMapping("/saveArticolo")
	public Boolean saveUser(@RequestBody ArticoloSaveRequest articoloReq) 
	{
		return articoloService.saveArticolo(articoloReq.getArticolo(), articoloReq.getUtenteUpdate());
	}
	
	
	@PostMapping("/deleteArticolo")
	public Boolean deleteArticolo(@RequestBody DeleteRequest articoloReq) 
	{
		return articoloService.deleteArticolo(articoloReq.getIdEntity(), articoloReq.getUtenteUpdate());
	}
	
	/***** DATA TABLE LIST *****/
	@PostMapping("/getArticoliDataTable")
	public ArticoliListOverview getArticoliDataTable(@RequestBody JsonNode payload)
	{
		return articoloService.getArticoliDataTable(payload);
	}
	
	
}
