package com.gamenet.cruscottofatturazione.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ProspectTotali", schema = "Anagraphics")
public class ProspectTopSummary {
	@Id
	private Integer richiesta_id;
	private Integer garanzie_prestate_numero;
	private Double garanzie_prestate_totale;
	private Integer garanzie_scadenza_numero;
	private Double garanzie_scadenza_totale;
	private Integer garanzie_deroga_numero;
	private Double garanzie_deroga_totale;
	private Integer garanzie_dovuto_numero;
	private Double garanzie_dovuto_totale;
	
}
