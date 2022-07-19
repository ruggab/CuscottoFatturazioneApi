package com.gamenet.cruscottofatturazione.models.response;

import com.gamenet.cruscottofatturazione.models.Fattura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveResponseFattura {
	private Boolean esito;
	private String message;
	private String errore;
	private Fattura fattura;
}
