package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RichiestaGaranzieScadenzeReview
{
	private Cliente clienteRif;
	private Integer countItem;
	private List<ProspectGaranzia> listGaranzie;
}
