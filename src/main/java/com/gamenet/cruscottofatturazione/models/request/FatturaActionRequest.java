package com.gamenet.cruscottofatturazione.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FatturaActionRequest {
	private Integer idFattura;
	private String utenteUpdate;
}
