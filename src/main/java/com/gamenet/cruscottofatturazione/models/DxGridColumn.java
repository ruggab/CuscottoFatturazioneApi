package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DxGridColumn {

	private Integer visibleIndex;
	private String dataField;
	private String name;
	private String dataType;
	private Boolean visible;
	private String filterValue;
}
