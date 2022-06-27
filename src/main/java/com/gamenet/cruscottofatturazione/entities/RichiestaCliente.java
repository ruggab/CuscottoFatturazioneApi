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
@Table(name = "richiesta", schema = "dbo")
public class RichiestaCliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer clienteId;
	private String codiceSala;
	private String codiceTir;
	// private Integer workflow_step_id;
	// private Integer stato_id;
	private Date dataAttivazione;
	private Boolean presenzaSocietaCollegate;
	private String societaCollegate;
	private Boolean presenzaUltimoBilancio;
	private Integer annoBilancio;
	private Boolean presenzaEventiNegativi;
	private String eventiNegativi;
	private Boolean presenzaEsitiPregressi;
	private Boolean sideLetter;
	private Integer canaleContrattoId;
	private Integer canaleSottoscrizioneId;
	private Integer derogaContrattoId;
	private String derogaMeritoUser;
	private Date derogaMeritoDate;
	private Integer derogaMeritoId;
	private Integer derogaMeritoNotaId;
	private Boolean salvata;
	private Integer workUserId;
	private String workUser;
	private Date workDate;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;

	@ManyToOne
	@JoinColumn(name = "workflowStepId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private WorkflowStep workflowStep;
	
	@ManyToOne
	@JoinColumn(name = "statoId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private StatoRichieste statoRichieste;
	
	@ManyToOne
	@JoinColumn(name = "statoSapId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private StatoRichieste statoSapRichieste;
	
	@ManyToOne
	@JoinColumn(name = "categoriaId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private CategoriaRichieste categoriaRichieste;
	
	@OneToMany(mappedBy = "richiesteNote", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<NotaRichieste> noteRichieste;
	
	@OneToMany(mappedBy = "richiesteRegistroEmail", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RegistroEmail> registroEmailRichieste;
	
	@OneToMany(mappedBy = "richiestaSale", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<SalaRichiesta> saleRichiesta;
	
	@OneToMany(mappedBy = "richiestaTimeline", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RichiestaTimeline> timelineRichiesta;
	
	@OneToMany(mappedBy = "richiesteRegistroNotifica", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RegistroNotifica> registroNotificheRichieste;
	
	@OneToMany(mappedBy = "richiestaGaranzia", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<ProspectGaranzia> garanzieRichiesta;

	@OneToMany(mappedBy = "richiestaInsoluti", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<Insoluto> listInsolutiRichiesta;
}