package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.entities.Societa;
import com.gamenet.cruscottofatturazione.models.request.SocietaSaveRequest;
import com.gamenet.cruscottofatturazione.services.interfaces.SocietaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("societa")
@CrossOrigin
@RequiredArgsConstructor
public class SocietaController {
	
	private final SocietaService societaService;
	
	
	@GetMapping("/getSocietaList")
	public List<Societa> getUserList() 
	{
		return societaService.getSocieta();
	}
	
	@PostMapping("/getSocietaById")
	public Societa getUserById(@RequestBody Integer societaId) 
	{
		return societaService.getSocietaById(societaId);
	}
	
	@PostMapping("/saveSocieta")
	public Boolean saveSocieta(@RequestBody SocietaSaveRequest societaReq) 
	{
		return societaService.saveSocieta(societaReq.getSocieta(), societaReq.getUtenteUpdate());
	}
	
//	
//	
//	@PostMapping("/deleteUser")
//	public Boolean deleteUser(@RequestBody DeleteRequest utenteReq) 
//	{
//		return userService.deleteUser(utenteReq.getIdEntity(), utenteReq.getUtenteUpdate());
//	}
	
	
}
