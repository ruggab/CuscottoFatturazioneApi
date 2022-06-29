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
@Table(name = "VWdashboardFatture", schema = "dbo")
public class VWDashboardTopSummary {
	@Id
	private String statoFattura;
	private Integer numero;
	private Double importo;
	
}