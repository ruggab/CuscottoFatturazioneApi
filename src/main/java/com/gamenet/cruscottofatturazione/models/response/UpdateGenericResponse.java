package com.gamenet.cruscottofatturazione.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateGenericResponse {
	private Boolean result;
	private String message;
}
