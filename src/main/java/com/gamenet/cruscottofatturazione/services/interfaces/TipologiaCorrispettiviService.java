package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;

public interface TipologiaCorrispettiviService {

	List<TipologiaCorrispettivi> getTipologiaCorrispettivi();
	TipologiaCorrispettivi getTipologiaCorrispettiviById(Integer articoloId);
	Boolean saveTipologiaCorrispettivi(TipologiaCorrispettivi tipologiaCorrispettivi, String utenteUpdate);
	Boolean deleteTipologiaCorrispettivi(Integer idEntity, String utenteUpdate);
	

}