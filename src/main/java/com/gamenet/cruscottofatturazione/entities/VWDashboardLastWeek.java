package com.gamenet.cruscottofatturazione.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VWdashboardFattureLastWeek", schema = "dbo")
public class VWDashboardLastWeek {
	
	@Id
	private Integer giorno;
	private String giornoSettimana;
	private Integer numero;
	
}