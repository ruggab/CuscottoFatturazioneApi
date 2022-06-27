package com.gamenet.cruscottofatturazione.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RichiestaCliente {
	private Integer id;
	private Integer clienteId;
	private String codiceSala;
	private String codiceTir;
	private WorkflowStep workflowStep;
	private StatoRichieste statoRichieste;
	private StatoRichieste statoSapRichieste;
	private Date dataAttivazione;
	private CategoriaRichieste categoriaRichieste;
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
	
	private List<NotaRichieste> noteRichiesta;
	
	private List<ProspectGaranzia> listGaranzieValutazione;
}