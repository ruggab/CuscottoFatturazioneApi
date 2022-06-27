package com.gamenet.cruscottofatturazione.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "documenti_cliente", schema = "dbo")
public class ProspectDocumento {
	@Id
	private Integer documentId;
	private Integer richiestaId;
	private Integer tipoDocumentoId;
	private String tipoDocumento;
	private String docName;
	private String docPath;
	private byte[] docBlob;
	private String docType;
	private String docExt;
	private String lastModeUser;
	private String lastModeDate;
	private Boolean isEdit;
	
	@OneToMany(mappedBy = "documentoTimelineAttachment", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RichiestaTimelineAttachment> attachmentTimelineRichiesta;
}
