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
@Table(name = "articolo", schema = "dbo")
public class Articolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String codiceArticolo;
	private String descrizione;
	private Date dataValidita;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
	
}