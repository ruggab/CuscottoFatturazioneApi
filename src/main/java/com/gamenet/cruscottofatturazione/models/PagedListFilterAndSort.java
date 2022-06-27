package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PagedListFilterAndSort {

	private List<ListFilter> filters;
	private List<ListSort> sort;
	private int pagesize;
	private int index;

}
