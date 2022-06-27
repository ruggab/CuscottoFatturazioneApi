package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProspectAnagrafica {
	
	private String cliente;
	private String nome;
	private String business;
	private String legalMail;

	private String codiceSAP;
	private String descrizioneSocieta;

	private Integer idTipologiaPartner;
	private String tipologiaPartner;
	
	private String codiceSala;
	private String codiceTir;
	private Integer numeroMacchineSala;
	
	private Integer totNumeroSale;
	private Integer totNumeroTir;
	private Integer totNumeroMacchine;

	private String referenteArea;
	private String mailReferente;
	private String indirizzo;
	
	private String telefonoReferente;
	private String codiceFiscaleReferente;
	private String partitaIva;
}
