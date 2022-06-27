package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProspectDocumento
{
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
}
