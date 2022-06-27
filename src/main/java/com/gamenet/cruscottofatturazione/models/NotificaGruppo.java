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
public class NotificaGruppo {
	private Integer id;
	
	private Notifica notifica;
	
	private GruppoUtenti gruppo;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
}
