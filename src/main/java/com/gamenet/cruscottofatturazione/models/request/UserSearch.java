package com.gamenet.cruscottofatturazione.models.request;

//import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSearch {
	
	private String name;
	private String email;
	private String username;
	private String ruolo;
	private boolean attivo;

}
