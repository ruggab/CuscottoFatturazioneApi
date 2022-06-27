package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.models.RoleVoceMenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleVoceMenuSaveRequest
{
	private RoleVoceMenu roleVoceMenu;
	private String utenteUpdate;
}
