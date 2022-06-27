package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VW_Sale_TIR_Richieste", schema = "dbo")
public class SaleTirRichiesta {
	private Integer salaId;
	private String codiceSala;
	private Integer numeroMacchine;
	private String createUserSala;
	private Date createDateSala;
	private String lastModUserSala;
	private Date lastModDateSala;
	
	@Id
	private Integer tirId;
	private String codiceTir;
	private String createUserTir;
	private Date createDateTir;
	private String lastModUserTir;
	private Date lastModDateTir;
	
	@ManyToOne
	@JoinColumn(name = "richiestaId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RichiestaCliente richiestaSaleTir;

}
