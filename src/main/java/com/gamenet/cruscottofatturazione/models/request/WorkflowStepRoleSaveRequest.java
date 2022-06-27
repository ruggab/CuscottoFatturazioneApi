package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.models.WorkflowStepRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStepRoleSaveRequest
{
	private WorkflowStepRole workflowStepRole;
	private String utenteUpdate;
}
