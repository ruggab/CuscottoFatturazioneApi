package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users", schema = "Anagraphics")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//private Integer role_id;
	private String name;
	private String email;
	private String username;
	private String password;
	private String societa;
	private String token;
	private Boolean isNew;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	private Date validFrom;
	private Date validTo;
	
	@ManyToOne
	@JoinColumn(name = "roleId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RoleUser ruoloUtente;
}