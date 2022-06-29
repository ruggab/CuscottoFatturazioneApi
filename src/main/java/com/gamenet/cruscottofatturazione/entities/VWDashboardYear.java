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
@Table(name = "VWdashboardFattureYear", schema = "dbo")
public class VWDashboardYear {
	
	@Id
	private Integer mese;
	private String nomeMese;
	private Integer numero;
	
}