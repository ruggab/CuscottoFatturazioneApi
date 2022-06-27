package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListFilter {

	private String name;
	private String operator;
	private String value;
	private List<String> valueList;
}
