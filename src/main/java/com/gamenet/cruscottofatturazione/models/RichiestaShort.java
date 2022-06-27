package com.gamenet.cruscottofatturazione.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RichiestaShort {
	private Integer id;
	private String nomeCliente;
	private String codiceCliente;
	private Integer clienteId;
	private Integer statoId;
	private String statoRichiesta;
	private String business;
}
