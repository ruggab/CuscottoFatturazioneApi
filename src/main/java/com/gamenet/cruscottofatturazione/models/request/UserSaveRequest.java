package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
	private User user;
	private String utenteUpdate;
}
