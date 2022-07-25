package com.gamenet.cruscottofatturazione.models.sap.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	String DATA_FLUSSO;
	String TIPO_OPERAZIONE;
	String ID_FATTURA;
	String KUNNR;	//Cliente
	String BUKRS;	//Societa
	String ARTICOLO;
	String TIPO_CORRISPETTIVO;	
	String NOTE;
	String IMP_IMPONIBILE;	
	String ID_TIBCO;
}
