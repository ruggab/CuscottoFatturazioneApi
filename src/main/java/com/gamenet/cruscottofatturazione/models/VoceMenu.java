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
public class VoceMenu {
	private Integer id;
	private String path;
	private String title;
	private String icon;
	private Integer orderNumber;
	private String cssClass;
	private String identifier;
	private Boolean isDettaglio;
	private List<VoceMenu> child;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
