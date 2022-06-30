package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente
{
	private Integer id;
	private String codiceCliente;
	private String societa;
	private String ragioneSociale;
	private String codiceFiscale;
	private String partitaIva;
	private String nazionalita;
	private String sedeLegale;
	private String appartieneGruppoIva;
	private String codiceDestinatarioFatturazione;
	private String modalitaPagamento;
	private String condizioniPagamento;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
}