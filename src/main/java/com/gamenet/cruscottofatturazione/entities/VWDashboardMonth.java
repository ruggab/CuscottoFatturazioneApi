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
@Table(name = "VWdashboardFattureMonth", schema = "dbo")
public class VWDashboardMonth {
	
	@Id
	private Integer settimana;
	private Integer numero;
	
}