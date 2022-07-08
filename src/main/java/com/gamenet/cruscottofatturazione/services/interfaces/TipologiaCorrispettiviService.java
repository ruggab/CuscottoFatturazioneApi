package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.models.response.TipologiaCorrispettiviListOverview;

public interface TipologiaCorrispettiviService {

	List<TipologiaCorrispettivi> getTipologiaCorrispettivi(Boolean soloAttivi);
	TipologiaCorrispettivi getTipologiaCorrispettiviById(Integer articoloId);
	Boolean saveTipologiaCorrispettivi(TipologiaCorrispettivi tipologiaCorrispettivi, String utenteUpdate);
	Boolean deleteTipologiaCorrispettivi(Integer idEntity, String utenteUpdate);
	/***** DATA TABLE LIST *****/
	TipologiaCorrispettiviListOverview getTipologiaCorrispettiviDataTable(JsonNode payload);
	

}