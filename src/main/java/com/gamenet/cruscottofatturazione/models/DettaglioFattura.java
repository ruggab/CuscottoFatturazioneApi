package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DettaglioFattura {
	private Integer id;
	private Integer idFattura;
	private Integer progressivoRiga;
	private String codiceArticolo;
	private String codiceCorrispettivo;
	private Double importo;
	private String note;
	private String idMessaggio;
	private String descrizioneMessaggio;

	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
	
}