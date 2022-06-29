package com.gamenet.cruscottofatturazione.models;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMonth implements Serializable
{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<DashboardSettimana> settimane;
	private Date lastUpdate;
	
	

}
