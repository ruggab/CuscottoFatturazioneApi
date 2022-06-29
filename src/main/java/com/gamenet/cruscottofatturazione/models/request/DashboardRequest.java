package com.gamenet.cruscottofatturazione.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardRequest {
	private Integer idRole;
	private Boolean isAdmin;
	private Boolean adminView;
	private String societa;
}
