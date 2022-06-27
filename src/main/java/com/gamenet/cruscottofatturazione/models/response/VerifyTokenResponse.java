package com.gamenet.cruscottofatturazione.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyTokenResponse {
	private Boolean isValid;
	private Boolean isRefresh;
	private String token;
	private String message;
}
