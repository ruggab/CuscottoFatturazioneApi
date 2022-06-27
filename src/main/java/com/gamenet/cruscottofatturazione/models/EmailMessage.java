package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {
	private String from;
	private String to;
	private String subject;
	private String message;
	private String attachments;
	private Boolean isResouceAttach;
	private Boolean isHtml;
	
	private String smtp_host;
	private String smtp_port;
	private String smtp_ssl_enable;	
	private Boolean smtp_auth;	
	private String smtp_auth_user;	
	private String smtp_auth_pwd;	
	private String smtp_default_from;
}
