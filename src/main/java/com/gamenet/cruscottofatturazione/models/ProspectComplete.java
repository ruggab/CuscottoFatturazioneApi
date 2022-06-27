package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProspectComplete {
	private Boolean result;
	private String resultMessage;
	private String codiceCliente;
	private String business;
	private ProspectTopSummary topSummary;
	private ProspectAnagrafica anagrafica;
	private List<RichiestaShort> listRichiesteAltriBusiness;
	private List<ProspectGaranzia> listGaranzie;
	private List<ProspectDocumento> listDocumenti;
	private RichiestaCliente richiesta;
}
