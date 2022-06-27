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
public class InsolutoSummary
{
	private Integer id;
	
	private RichiestaCliente richiesta;
	
	private StatoInsoluti statoInsoluto;

	private String codiceCliente;
	private String nomeCliente;
	private String business;
	
	private Double corrente;
	private Double scaduto;
	private Double esposizione;
	
	private Date dataRiferimento;
	private Date scadenzaRendimento;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
