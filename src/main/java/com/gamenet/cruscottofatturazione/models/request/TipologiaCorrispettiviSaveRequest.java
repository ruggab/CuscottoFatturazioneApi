package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipologiaCorrispettiviSaveRequest {
	private TipologiaCorrispettivi tipologiaCorrispettivi;
	private String utenteUpdate;
}
