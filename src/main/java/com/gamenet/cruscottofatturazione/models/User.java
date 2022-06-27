package com.gamenet.cruscottofatturazione.models;

import java.util.Date;
import java.util.List;

//import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	private Integer id;
	private RoleUser ruoloUtente;
	
	// private Integer role_id;
	// @Column(nullable = true)
	// private String role_name;
	
	private String name;
	private String email;
	private String username;
	private String password;
	private String token;
	private Boolean isNew;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	private Date validFrom;
	private Date validTo;
	private List<String> avaiableBusiness;
	private List<VoceMenu> vociUtente;
}
