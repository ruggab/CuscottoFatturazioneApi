package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DerogaMeritoResult {
	@Id
	private Integer nextWorkFlowStepId;
	private Integer nextWorkFlowStepIndex;
	
	private Integer derogaMeritoId;
	private String derogaMeritoUser;
	private Date derogaMeritoDate;
	private Integer derogaMeritoNotaId;
	private String derogaMeritoNota;
}
