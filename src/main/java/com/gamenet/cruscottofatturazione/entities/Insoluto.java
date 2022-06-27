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
@Table(name = "insoluti", schema = "dbo")
public class Insoluto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
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
	private RichiestaCliente richiestaInsoluti;
	
	@Nullable
	@ManyToOne
	@JoinColumn(name = "statoId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private StatoInsoluti statoInsoluti;
	
	@OneToMany(mappedBy = "sollecitoInsoluti", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Sollecito> listSollecitiInsoluto;
	
	@OneToMany(mappedBy = "incassoInsoluto", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Incasso> listIncassiInsoluto;
}
