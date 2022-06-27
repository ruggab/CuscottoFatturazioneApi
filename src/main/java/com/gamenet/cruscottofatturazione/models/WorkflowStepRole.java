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
public class WorkflowStepRole 
{
	private Integer id;
	private RoleUser ruoloWorkflowStep;
	private WorkflowStep workflowStepRuolo;
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
}
