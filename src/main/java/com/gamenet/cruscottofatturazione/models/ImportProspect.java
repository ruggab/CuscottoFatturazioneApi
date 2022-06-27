package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImportProspect {
	public String TIMESTAMP;
	public String KUNNR;
	public String REF_AREA;
	public String MAIL_REF_AREA;
	public String NOME;
	public String TIPO_CLI;
	public String P_IVA;
	public String COD_FISC;
	public String INDIRIZZO;
	
	public String TEL_NUMBER;
	
	public String MAIL;
	public String TIPO_MAIL;
	
	public String SOCIETA;
	public String ZTP_BUSINESS;
	public String STATUS;
	public String CODE_AGEING;
	public String COD_FSC;
	public String REND_MEDIO;
	
	public String ZBANCA_EMITTENTE;
	public String ZDATA_SCADENZA;
	public String ZGAR_DOV_VLT;
	public String ZGAR_DOV_AWP;
	public String ZGAR_DOV_SCO;
	public String ZTP_GARANZIA;
	public String ZDATA_EMIS;
	public String ZRINNOVOAA;
	public String ZDATA_ESCUSSIONE;
	public String ZDTERM_ESCUSSIONE;
	public String ZRINNOVOATE;
	public String STATO_POLIZZA;
	public String TOTALE;
	public String ZDATA_DELETE;
	
	public String ZCOD_G4_SEDE;
	public String ZDIRITTO;
	public String NUM_AWP;
	public String NUM_VLT;
	public String NUM_M_RIC;
	
	public String TIR;
}
