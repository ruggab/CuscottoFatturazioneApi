package com.gamenet.cruscottofatturazione.models.response;

import java.util.List;

import com.gamenet.cruscottofatturazione.models.Articolo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticoliListOverview {
	private Integer totalCount;
	private List<Articolo> lines;
}
