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
public class DashboardClientiAttiviChart {
	private Integer gennaio;
	private Integer febbraio;
	private Integer marzo;
	private Integer aprile;
	private Integer maggio;
	private Integer giugno;
	private Integer luglio;
	private Integer agosto;
	private Integer settembre;
	private Integer ottobre;
	private Integer novembre;
	private Integer dicembre;
	private Integer maxValue;
	private Date lastModDate;
}
