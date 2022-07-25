package com.gamenet.cruscottofatturazione.models.response;

import com.gamenet.cruscottofatturazione.models.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveResponseCliente {
	private Cliente cliente;
	private Boolean esito;
	private String message;
	private String errore;
}
