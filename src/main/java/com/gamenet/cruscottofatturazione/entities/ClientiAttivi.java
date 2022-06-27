package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

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
@Table(name = "ClientiAttivi", schema = "Statistics")
public class ClientiAttivi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer anno;
	private Integer mese;
	private Integer clientiAttivi;
	private Integer giornoAggiornamento;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
