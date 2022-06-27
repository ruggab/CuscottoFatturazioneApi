package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "stato_insoluti", schema = "dbo")
public class StatoInsoluti {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Integer indice;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	
	@OneToMany(mappedBy = "statoInsolutiSolleciti", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Sollecito> listSollecitiStatoInsoluto;
	
	@OneToMany(mappedBy = "statoInsoluti", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Insoluto> listInsolutiStato;
}
