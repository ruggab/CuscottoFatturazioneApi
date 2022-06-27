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
public class WorkflowStep {
	private Integer id;
	private Workflow workflow;
	private String nomeStep;
	private Integer tabIndex;
	private Boolean attivato;
	private Boolean notifica;
	private Boolean definitivo;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
