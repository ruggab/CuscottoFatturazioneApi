package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleTirRichiesta {
	private Integer salaId;
	private RichiestaCliente richiesta;
	private String codiceSala;
	private Integer numeroMacchine;
	private String createUserSala;
	private Date createDateSala;
	private String lastModUserSala;
	private Date lastModDateSala;

	private Integer tirId;
	private String codiceTir;
	private String createUserTir;
	private Date createDateTir;
	private String lastModUserTir;
	private Date lastModDateTir;
}
