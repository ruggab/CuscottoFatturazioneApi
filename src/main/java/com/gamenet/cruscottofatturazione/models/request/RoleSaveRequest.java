package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.models.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveRequest {
	private RoleUser role;
	private String utenteUpdate;
}
