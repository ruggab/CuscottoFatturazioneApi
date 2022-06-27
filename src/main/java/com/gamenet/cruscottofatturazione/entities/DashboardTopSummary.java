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
@Table(name = "vw_uff_garanzie_dash_numeri", schema = "dbo")
public class DashboardTopSummary {
	@Id
	private Integer box;
	private Integer primario;
	private String secondario;
}
