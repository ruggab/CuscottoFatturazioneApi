package com.gamenet.cruscottofatturazione.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "HistoryNotifiche", schema = "Logs")
public class HistoryNotificheUtente 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer utenteId;
	private Integer notificaId;
	private Boolean isNew;
	private String message;
	private String urlToOpen;
	private Integer notificaStepId;
	private String notificaNome;
	private Integer tipologiaId;
	private String tipologiaNome;
	private String tipologiaDescrizione;	
}
