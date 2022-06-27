package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.entities.Fattura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FatturaSaveRequest {
	private Fattura fattura;
	private String utenteUpdate;
}
