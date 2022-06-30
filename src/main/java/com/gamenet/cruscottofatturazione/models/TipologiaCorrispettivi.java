package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipologiaCorrispettivi {
	private Integer id;
	private String codiceCorrispettivo;
	private String descrizione;
	private Date dataValidita;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
	
}