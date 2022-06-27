package com.gamenet.cruscottofatturazione.models.request;

import com.gamenet.cruscottofatturazione.entities.ClienteCruscotto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteSaveRequest {
	private ClienteCruscotto cliente;
	private String utenteUpdate;
}
