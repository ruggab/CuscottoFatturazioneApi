package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fattura", schema = "dbo")
public class Fattura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String societa;
	private Date dataFattura;
	private String tipologiaFattura;
	//private String codiceCliente;
	@ManyToOne
	@JoinColumn(name = "codice_cliente")
	private Cliente cliente;
	private Double importo;
	private String statoFattura;
	private String esitoInvio;
	private Date dataInvioFlusso;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="idFattura")
	@OrderBy("progressivoRiga ASC")
	private Set<DettaglioFattura> listaDettaglioFattura;
	
	private String create_user;
	private Date create_date;
	private String last_mod_user;
	private Date last_mod_date;
	
}