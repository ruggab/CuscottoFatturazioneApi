package com.gamenet.cruscottofatturazione.models;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardLastWeek implements Serializable
{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer lunedi=0;
	private Integer martedi=0;
	private Integer mercoledi=0;
	private Integer giovedi=0;
	private Integer venerdi=0;
	private Integer sabato=0;
	private Integer domenica=0;
	
	private Date lastUpdate;
	private Double incrementoSettimana;
	
	

}
