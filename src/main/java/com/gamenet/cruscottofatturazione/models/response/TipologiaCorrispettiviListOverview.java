package com.gamenet.cruscottofatturazione.models.response;

import java.util.List;

import com.gamenet.cruscottofatturazione.models.TipologiaCorrispettivi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TipologiaCorrispettiviListOverview {
	private Integer totalCount;
	private List<TipologiaCorrispettivi> lines;
}
