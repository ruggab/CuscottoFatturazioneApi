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
@Table(name = "Workflow_step", schema = "dbo")
public class WorkflowStep {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//private Integer workflow_id;
	private String nomeStep;
	private Integer tabIndex;
	private Boolean attivato;
	private Boolean notifica;
	private Boolean definitivo;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;


	@ManyToOne
	@JoinColumn(name = "workflowId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Workflow workflow;
	
	@OneToMany(mappedBy = "workflowStep", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RichiestaCliente> richiesteCliente;
	
	@OneToMany(mappedBy = "workflowStepNote", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<NotaRichieste> noteRichieste;
	
	@OneToMany(mappedBy = "workflowStepNotifiche", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Notifica> stepNotifica;
	
	@OneToMany(mappedBy = "workflowStepRegistroEmail", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RegistroEmail> stepRegistroEmail;
	
	@OneToMany(mappedBy = "workflowStepRuolo", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<WorkflowStepRole> workflowStepRole;
}
