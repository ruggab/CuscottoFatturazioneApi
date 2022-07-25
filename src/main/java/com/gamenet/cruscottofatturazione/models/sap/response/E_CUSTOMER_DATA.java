package com.gamenet.cruscottofatturazione.models.sap.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class E_CUSTOMER_DATA {

	String KUNNR; //cliente
	String BUKRS; //societa
	String NAME;  //ragione sociale
	String STCD1; //codice fiscale
	String STCD2; // partita iva
	String LAND1; //nazionalita
	String ADDRESS;//sede legale
	String ZGIVA; //appartenenza al gruppo
	String STCD4; //codice destinatario
	String ZWELS;//metodi pagamento
	String ZTERM; //termini pagamento
	String ESITO; //esito
	String IDMESSAGGIO; //id
	String DESCRIZIONE; //messaggio
	
}
