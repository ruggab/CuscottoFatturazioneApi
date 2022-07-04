package com.gamenet.cruscottofatturazione.models;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Fattura {
	private Integer id;
	private String societa;
	private Date dataFattura;
	private String tipologiaFattura;
	//private String codiceCliente;
	private Cliente cliente;
	private Double importo;
	private String statoFattura;
	private String esitoInvio;
	private Date dataInvioFlusso;
	
	
	private List<DettaglioFattura> listaDettaglioFattura;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
	
}