package com.gamenet.cruscottofatturazione.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAutoComplete
{
	private String codiceCliente;
	private String ragioneSociale;
	private String partitaIva;
}