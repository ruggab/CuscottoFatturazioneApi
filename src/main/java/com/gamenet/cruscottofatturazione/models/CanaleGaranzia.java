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
public class CanaleGaranzia {
	private Integer id;
	private String nome;
	private String templateOggettoEmail;
	private String templateTestoEmail;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
