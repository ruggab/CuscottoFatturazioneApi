package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Roles", schema = "Anagraphics")
public class RoleUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@OneToMany(mappedBy = "ruoloUtente", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<User> listaUtenti;
	
	@OneToMany(mappedBy = "ruoloWorkflowStep", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<WorkflowStepRole> listaWorkflowStepRole;
	
	@OneToMany(mappedBy = "ruoloVoceMenu", fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RoleVoceMenu> listaRuoloVoceMenu;
}
