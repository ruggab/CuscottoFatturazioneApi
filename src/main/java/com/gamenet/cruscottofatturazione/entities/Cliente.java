package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cliente", schema = "dbo")
public class Cliente {
	//@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
	@Id
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