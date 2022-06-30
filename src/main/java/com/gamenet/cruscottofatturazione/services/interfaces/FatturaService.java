package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.Fattura;

public interface FatturaService {

	List<Fattura> getFatture();
	List<com.gamenet.cruscottofatturazione.models.Fattura> getLastTenFatturaBySocieta(String codiceSocieta);
	Fattura getFatturaById(Integer fatturaId);
	Fattura saveFattura(Fattura fattura, String utenteUpdate);
	com.gamenet.cruscottofatturazione.models.Fattura saveFatturaConDettagli(com.gamenet.cruscottofatturazione.models.Fattura fattura, String utenteUpdate);
	List<com.gamenet.cruscottofatturazione.models.Fattura> getFatture(String codiceSocieta);

}