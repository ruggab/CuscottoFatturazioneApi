package com.gamenet.cruscottofatturazione.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
	private Integer idUtente;
	private String passwordPrecedente;
	private String passwordNuova;
	private String utenteUpdate;
}
