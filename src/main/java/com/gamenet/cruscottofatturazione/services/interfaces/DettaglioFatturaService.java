package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;

public interface DettaglioFatturaService {

	List<DettaglioFattura> getDettaglioFatture();
	DettaglioFattura getDettaglioFatturaById(Integer dettaglioFatturaId);
	List<DettaglioFattura> getDettaglioFatturaByFatturaId(Integer fatturaId);
	DettaglioFattura saveDettaglioFattura(DettaglioFattura dettaglioFattura, String utenteUpdate);
	Boolean deleteDettaglioFattura(Integer dettaglioFatturaId, String utenteUpdate);
	

}