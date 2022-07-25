package com.gamenet.cruscottofatturazione.models.sap.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("DATA_FLUSSO")
	String DATA_FLUSSO;
	@JsonProperty("TIPO_OPERAZIONE")
	String TIPO_OPERAZIONE;
	@JsonProperty("ID_FATTURA")
	String ID_FATTURA;
	@JsonProperty("KUNNR")
	String KUNNR;	//Cliente
	@JsonProperty("BUKRS")
	String BUKRS;	//Societa
	@JsonProperty("ARTICOLO")
	String ARTICOLO;
	@JsonProperty("TIPO_CORRISPETTIVO")
	String TIPO_CORRISPETTIVO;	
	@JsonProperty("NOTE")
	String NOTE;
	@JsonProperty("IMP_IMPONIBILE")
	String IMP_IMPONIBILE;
	@JsonProperty("ID_TIBCO")
	String ID_TIBCO;
}
