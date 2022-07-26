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
public class DashboardTopSummary
{
	private Integer fattureEmesse;
	private Date dataFattureEmesse;

	private Integer fattureConvalidate;
	private Date dataFattureConvalidate;

	private Integer fattureRifiutate;
	private Integer totaleFatture;

	private Double totaleImportoFatture;
	private Date dataImportoFatture;

}
