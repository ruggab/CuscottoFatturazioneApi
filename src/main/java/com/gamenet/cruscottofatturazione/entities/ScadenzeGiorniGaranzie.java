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
@Table(name = "scadenze_giorni_garanzie", schema = "Anagraphics")
public class ScadenzeGiorniGaranzie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tipologia;
	private Integer giorni;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;

}
