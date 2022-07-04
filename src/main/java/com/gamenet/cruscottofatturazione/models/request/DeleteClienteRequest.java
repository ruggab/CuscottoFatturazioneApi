package com.gamenet.cruscottofatturazione.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClienteRequest {
	private String codiceCliente;
	private String utenteUpdate;
}
