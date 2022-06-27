package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProspectGaranzia {
	private Integer idGaranzia;
	private Integer idRichiesta;
	
	private String codice;
	private Integer idStato;
	private String nomeStato;
	private Integer percStato;

	private String descrizione;
	private String tipologia;
	private String ente;
	private String data;
	
	private Double dovuto;
	private Double prestato;
	
	private Boolean deroga;
	private String noteDeroga;
	
	private String protocollo;
	private String diritto;
	
	private String dataScadenza;

}
