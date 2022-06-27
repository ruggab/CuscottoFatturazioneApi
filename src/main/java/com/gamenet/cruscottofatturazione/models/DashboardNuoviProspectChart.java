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
public class DashboardNuoviProspectChart {
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
	private Integer maxValue;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
