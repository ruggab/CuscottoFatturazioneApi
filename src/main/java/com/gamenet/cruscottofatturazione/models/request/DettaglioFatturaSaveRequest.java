package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DettaglioFatturaSaveRequest {
	private DettaglioFattura dettaglioFattura;
	private String utenteUpdate;
}
