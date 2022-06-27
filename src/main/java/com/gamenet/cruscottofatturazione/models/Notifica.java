package com.gamenet.cruscottofatturazione.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notifica {
	private Integer id;
	private String nome;
	private String messaggioNotifica;
	private String oggettoEmail;
	private String testoEmail;
	private String urlToOpen;
	
	private Integer esitoId;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	
	private WorkflowStep workflowStepNotifiche;
	
	private NotificaTipologia tipologiaNotifica;
	
	private List<User> utentiNotifica;
	private List<GruppoUtenti> gruppiNotifica;
}
