package com.gamenet.cruscottofatturazione.models.sap.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatiContabiliResponse {
	ES_RETURN ES_RETURN;
	ET_CFA_OUTPUT ET_CFA_OUTPUT;
}
