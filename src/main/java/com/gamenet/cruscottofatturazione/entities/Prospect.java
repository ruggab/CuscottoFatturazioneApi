package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

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
@Table(name = "VW_ALL_Prospect", schema = "dbo")
public class Prospect {
	@Id
	private Integer id;
	private String codiceCliente;
	private String nomeCliente;
	private String business;
	private Integer workflowStepId;
	private String nomeStep;
	private Integer sommaGaranzie;
	private Date lastMod;
	private Integer workUserId;
}
