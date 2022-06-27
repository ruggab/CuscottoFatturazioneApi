package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNotificheUtente {
	private Integer id;
	private String message;
	private String urlToOpen;
	private Boolean isNew;
	private Integer notificaId;
	private Integer notificaStepId;
	private String notificaNome;
	private Integer tipologiaId;
	private String tipologiaNome;
	private String tipologiaDescrizione;
}
