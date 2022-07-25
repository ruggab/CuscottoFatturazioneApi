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
public class ES_RETURN implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("ESITO")
	String ESITO;	
	@JsonProperty("IDMESSAGGIO")
	String IDMESSAGGIO;	
	@JsonProperty("DESCRIZIONE")
	String DESCRIZIONE;
}
