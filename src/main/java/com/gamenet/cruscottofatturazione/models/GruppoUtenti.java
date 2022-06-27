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
public class GruppoUtenti {
	private Integer id;
	private String name;
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
}
