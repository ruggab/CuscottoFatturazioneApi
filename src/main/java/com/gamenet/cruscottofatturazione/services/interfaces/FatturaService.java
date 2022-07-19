package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.StatoFatturaLog;
import com.gamenet.cruscottofatturazione.models.response.FattureListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponseFattura;

public interface FatturaService {

	List<Fattura> getFatture();
	List<com.gamenet.cruscottofatturazione.models.Fattura> getLastTenFatturaBySocieta(String codiceSocieta);
	com.gamenet.cruscottofatturazione.models.Fattura getFatturaById(Integer fatturaId);
	Boolean saveFattura(Fattura fattura, String utenteUpdate);
	SaveResponseFattura saveFatturaConDettagli(com.gamenet.cruscottofatturazione.models.Fattura fattura, String utenteUpdate);
	List<com.gamenet.cruscottofatturazione.models.Fattura> getFatture(String codiceSocieta);
	FattureListOverview getFattureDataTable(JsonNode payload,String codiceSocieta);
	Boolean rifiutaFattura(Integer idFattura, String utenteUpdate);
	Boolean inoltraFattura(Integer idFattura, String utenteUpdate);
	Boolean validaFattura(Integer idFattura, String utenteUpdate);
	List<StatoFatturaLog> getLogStatoFattura(Integer idFattura);

}