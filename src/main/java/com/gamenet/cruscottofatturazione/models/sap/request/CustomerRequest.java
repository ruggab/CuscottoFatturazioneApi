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
public class CustomerRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("I_BUKRS")
	String I_BUKRS; //cliente
	@JsonProperty("I_KUNNR")
	String I_KUNNR; //societa
}
