package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProspectTopSummary {
	private Integer richiestaId;
	
	private Integer numGaranziePrestate;
	private Double totaleGaranziePrestate;

	private Integer numGaranzieScadenze;
	private Double totaleGaranzieScadenze;

	private Integer numGaranzieDeroga;
	private Double totaleGaranzieDeroga;

	private Integer numPrestatoDovuto;
	private Double totalePrestatoDovuto;
}
