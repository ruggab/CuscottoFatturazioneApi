package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VW_ALL_Insoluti", schema = "dbo")
public class InsolutoSummary {
	@Id
	private Integer id;

	private String codiceCliente;
	private String nomeCliente;
	private String business;
	
	private String statoInsoluto;
	
	private Double corrente;
	private Double scaduto;
	private Double esposizione;
	
	private Date dataRiferimento;
	private Date scadenzaRendimento;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	
	@ManyToOne
	@JoinColumn(name = "richiestaId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RichiestaCliente richiestaInsolutiSummary;
	
	@Nullable
	@ManyToOne
	@JoinColumn(name = "statoId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private StatoInsoluti statoInsolutiSummary;
}
