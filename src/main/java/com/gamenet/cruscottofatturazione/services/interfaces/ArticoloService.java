package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.models.response.ArticoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;

public interface ArticoloService {

	List<Articolo> getArticoli(Boolean soloAttivi);
	Articolo getArticoloById(Integer articoloId);
	SaveResponse saveArticolo(Articolo articolo, String utenteUpdate);
	Boolean deleteArticolo(Integer articoloId, String utenteUpdate);
	/***** DATA TABLE LIST *****/
	ArticoliListOverview getArticoliDataTable(JsonNode payload);
	

}