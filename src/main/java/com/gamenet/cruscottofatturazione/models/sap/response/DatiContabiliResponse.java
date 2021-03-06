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
public class DatiContabiliResponse  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("ES_RETURN")
	ES_RETURN ES_RETURN;
	@JsonProperty("ET_CFA_OUTPUT")
	ET_CFA_OUTPUT ET_CFA_OUTPUT;
}
