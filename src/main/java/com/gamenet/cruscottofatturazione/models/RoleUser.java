package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleUser {
	private Integer id;
	private String name;
	private String description;
	private Boolean isAdmin;
	private String email;
	private Boolean deleted;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
