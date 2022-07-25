package com.gamenet.cruscottofatturazione.models.sap.response;

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
public class E_CUSTOMER_DATA implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("KUNNR")
	String KUNNR; //cliente
	@JsonProperty("BUKRS")
	String BUKRS; //societa
	@JsonProperty("NAME")
	String NAME;  //ragione sociale
	@JsonProperty("STCD1")
	String STCD1; //codice fiscale
	@JsonProperty("STCD2")
	String STCD2; // partita iva
	@JsonProperty("LAND1")
	String LAND1; //nazionalita
	@JsonProperty("ADDRESS")
	String ADDRESS;//sede legale
	@JsonProperty("ZGIVA")
	String ZGIVA; //appartenenza al gruppo
	@JsonProperty("STCD4")
	String STCD4; //codice destinatario
	@JsonProperty("ZWELS")
	String ZWELS;//metodi pagamento
	@JsonProperty("ZTERM")
	String ZTERM; //termini pagamento
	@JsonProperty("ESITO")
	String ESITO; //esito
	@JsonProperty("IDMESSAGGIO")
	String IDMESSAGGIO; //id
	@JsonProperty("DESCRIZIONE")
	String DESCRIZIONE; //messaggio
	
}
