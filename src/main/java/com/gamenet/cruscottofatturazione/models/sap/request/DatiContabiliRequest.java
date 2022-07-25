package com.gamenet.cruscottofatturazione.models.sap.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatiContabiliRequest {
	
	List<Item> item;
}
