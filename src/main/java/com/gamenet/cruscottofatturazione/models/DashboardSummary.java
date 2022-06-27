package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummary {
	private DashboardTopSummary topDashboardSummary;
	
	private List<Cliente> bottomDashboardClienti;
	
	private List<Cliente> bottomDashboardProspect;
}
