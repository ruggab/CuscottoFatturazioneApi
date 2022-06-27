package com.gamenet.cruscottofatturazione.models.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardProspectRequest {
	private Integer idRole;
	private Boolean isAdmin;
	private Boolean adminView;
	private ArrayList<String> selectedBusiness;
}
