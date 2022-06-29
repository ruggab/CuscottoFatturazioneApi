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
public class DashboardYear implements Serializable
{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer gennaio=0;
	private Integer febbraio=0;
	private Integer marzo=0;
	private Integer aprile=0;
	private Integer maggio=0;
	private Integer giugno=0;
	private Integer luglio=0;
	private Integer agosto=0;
	private Integer settembre=0;
	private Integer ottobre=0;
	private Integer novembre=0;
	private Integer dicembre=0;
	
	
	private Date lastUpdate;
	
	

}
