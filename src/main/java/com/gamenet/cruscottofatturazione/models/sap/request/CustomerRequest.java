package com.gamenet.cruscottofatturazione.models.sap.request;

import com.gamenet.cruscottofatturazione.entities.Articolo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

	String I_BUKRS; //cliente
	String I_KUNNR; //societa
}
