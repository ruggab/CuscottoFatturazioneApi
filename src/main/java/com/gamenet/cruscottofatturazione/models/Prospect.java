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
public class Prospect
{
	private Integer id;
	private String codiceCliente;
	private String nomeCliente;
	private String business;
	private String workflowStepId;
	private String nomeStep;
	private Integer sommaGaranzie;
	private Date lastMod;
	private Integer workUserId;
}
