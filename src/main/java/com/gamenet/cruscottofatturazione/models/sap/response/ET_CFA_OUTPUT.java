package com.gamenet.cruscottofatturazione.models.sap.response;

import java.util.List;

import com.gamenet.cruscottofatturazione.models.sap.request.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ET_CFA_OUTPUT {
	
	List<Item> item;
}
