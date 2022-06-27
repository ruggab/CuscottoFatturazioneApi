package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.Societa;

public interface SocietaService {

	List<Societa> getSocieta();
	Societa getSocietaById(Integer societaId);
	Boolean saveSocieta(Societa societa, String utenteUpdate);

	

}