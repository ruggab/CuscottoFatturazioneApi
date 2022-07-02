package com.gamenet.cruscottofatturazione.Enum;

public enum StatoFattura {
	VALIDATA("V","Validata"),
	IN_COMPILAZIONE("","In compilazione"),
	RIFIUTATA("R","Rifiutata"),
	DA_APPROVARE("D","Da Approvare"),
	CONTABILIZZATA("C","Contabilizzata"),
	RIGETTATA_DA_SAP("G","Rigettata da SAP"),
	VALIDATA_DA_SAP("S","Validata da SAP");


	private final String key;
	private final String value;


	StatoFattura(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}

}
