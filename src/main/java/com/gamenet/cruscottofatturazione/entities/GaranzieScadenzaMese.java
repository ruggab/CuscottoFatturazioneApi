package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GaranzieScadenzaMese {
	private Integer anno;
	private Integer mese;
	@Id
	private Integer giorno;
	private Integer numeroScadenze;
	private Date lastDate;
}
