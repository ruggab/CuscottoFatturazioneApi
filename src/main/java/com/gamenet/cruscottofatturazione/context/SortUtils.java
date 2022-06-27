package com.gamenet.cruscottofatturazione.context;

import org.springframework.data.domain.Sort.Direction;

public class SortUtils {

	public static Direction translateSort(String direction) {
		if (direction.equalsIgnoreCase("ASC"))
			return Direction.ASC;
		else
			return Direction.DESC;
	}

}