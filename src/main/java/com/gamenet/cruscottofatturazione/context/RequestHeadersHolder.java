package com.gamenet.cruscottofatturazione.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@RequestScope
@ToString
public class RequestHeadersHolder {

	private String idToken;
	private String user;
	private String userAgent;
	private String uri;
	
}
