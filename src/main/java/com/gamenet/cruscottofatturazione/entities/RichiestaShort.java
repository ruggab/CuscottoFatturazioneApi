package com.gamenet.cruscottofatturazione.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RichiestaShort {
	@Id
	private Integer id;
	private String nomeCliente;
	private String codiceCliente;
	private Integer clienteId;
	private Integer statoId;
	private String statoRichiesta;
	private String business;
}
