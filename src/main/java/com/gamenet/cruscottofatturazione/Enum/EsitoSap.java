package com.gamenet.cruscottofatturazione.Enum;

public enum EsitoSap {
	ERRORE("E"),
	SUCCESSO("S"),
	INFORMAZIONE("I");

	private final String value;


	EsitoSap(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
