package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.Articolo;

public interface ArticoloService {

	List<Articolo> getArticoli();
	Articolo getArticoloById(Integer articoloId);
	Boolean saveArticolo(Articolo articolo, String utenteUpdate);
	Boolean deleteArticolo(Integer articoloId, String utenteUpdate);
	

}