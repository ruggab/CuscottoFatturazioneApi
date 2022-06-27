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
public class RoleVoceMenu
{
	private Integer id;
	private RoleUser ruoloVoceMenu;
	private VoceMenu voceMenuRuolo;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
