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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifica", schema = "dbo")
public class Notifica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String messaggioNotifica;
	private String oggettoEmail;
	private String testoEmail;
	private String urlToOpen;
	
	private Integer esitoId;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;

	@ManyToOne
	@JoinColumn(name = "stepId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private WorkflowStep workflowStepNotifiche;

	@ManyToOne
	@JoinColumn(name = "tipologiaId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private NotificaTipologia tipologiaNotifica;
	
	@OneToMany(mappedBy = "notifica", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<NotificaUtente> notificheUtente;
	
	@OneToMany(mappedBy = "notifica", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<NotificaGruppo> notificheGruppo;
}
