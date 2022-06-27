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
@Table(name = "NuoviProspect", schema = "Statistics")
public class NuoviProspect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer anno;
	private Integer settimana;
	private Integer lunedi;
	private Integer martedi;
	private Integer mercoledi;
	private Integer giovedi;
	private Integer venerdi;
	private Integer sabato;
	private Integer domenica;
	private Integer totale;
	private Integer aumento;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
