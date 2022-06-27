package com.gamenet.cruscottofatturazione.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileServerData {
	private Integer documentId;
	private String documentName;
	private String bytes;
	private String type;
}
