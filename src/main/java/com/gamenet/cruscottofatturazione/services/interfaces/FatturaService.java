package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.Fattura;

public interface FatturaService {

	List<Fattura> getFatture();
	List<Fattura> getLastTenFatturaBySocieta(String codiceSocieta);
	Fattura getFatturaById(Integer fatturaId);
	Boolean saveFattura(Fattura fattura, String utenteUpdate);

}