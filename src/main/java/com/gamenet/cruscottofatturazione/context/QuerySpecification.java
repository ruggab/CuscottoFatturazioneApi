package com.gamenet.cruscottofatturazione.context;

import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.RoleUser;
import com.gamenet.cruscottofatturazione.entities.RoleVoceMenu;
import com.gamenet.cruscottofatturazione.entities.User;
import com.gamenet.cruscottofatturazione.entities.VoceMenu;
import com.gamenet.cruscottofatturazione.models.ListFilter;

public class QuerySpecification<T> implements Specification<T> {

	private static final long serialVersionUID = -6131105097490606457L;

	private ListFilter filterCriteria;

	public QuerySpecification() {
		super();
	}

	public QuerySpecification(ListFilter filterCriteria) {
		super();
		this.filterCriteria = filterCriteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (this.filterCriteria == null) {
			return builder.equal(builder.literal(1), 1);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String operator = this.filterCriteria.getOperator();
		String value = this.filterCriteria.getValue();
		String name = this.filterCriteria.getName();
		List<String> valueList = this.filterCriteria.getValueList();

		/*
		if (name.equalsIgnoreCase("days.openWeekendId")) {
			Join<OpenWeekendDays, OpenWeekend> daysOwe = root.join("openWeekend");
			return builder.equal(daysOwe.get("id"), Integer.parseInt(value));
		}
		*/
		
		/*if (name.equalsIgnoreCase("validTo"))
		{
			String finalColumn = "validTo";
			
			Date result = null;
			try {
				result = df.parse(value);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return builder.greaterThanOrEqualTo(root.<Date>get(finalColumn), result);
		}*/
		
		// CLIENTI - CUSTOMER
		if(name.equalsIgnoreCase("inScadenza") || name.equalsIgnoreCase("scaduta") || name.equalsIgnoreCase("rinnovo")) {
			Boolean boolValue = false;
			if(value.equals("true"))
			{
				boolValue = true;
			}
			
			return builder.equal(root.<Boolean>get(name), boolValue);
		}
		
		// VOCI MENU - RUOLI
		
		if(name.equalsIgnoreCase("ruoloVoceMenu.name")) {

			Join<RoleVoceMenu, RoleUser> ruoloVoceMenuJoin = root.join("ruoloVoceMenu");
			
			String finalColumn = "name";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(ruoloVoceMenuJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(ruoloVoceMenuJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(ruoloVoceMenuJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(ruoloVoceMenuJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(ruoloVoceMenuJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(ruoloVoceMenuJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		if(name.equalsIgnoreCase("voceMenuRuolo.title") || name.equalsIgnoreCase("voceMenuRuolo.path")) {

			Join<RoleVoceMenu, VoceMenu> voceMenuJoin = root.join("voceMenuRuolo");
			
			String finalColumn = "";
			
			if(name.equalsIgnoreCase("voceMenuRuolo.title"))
			{
				finalColumn = "title";
			}
			else if(name.equalsIgnoreCase("voceMenuRuolo.path"))
			{
				finalColumn = "path";
			}
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(voceMenuJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(voceMenuJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(voceMenuJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(voceMenuJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(voceMenuJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(voceMenuJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		// CLIENTE
		
		if (name.equalsIgnoreCase("cliente.codiceCliente"))
		{
			Join<Fattura, Cliente> clienteJoin = root.join("cliente");
			
			String finalColumn = "codiceCliente";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		if (name.equalsIgnoreCase("cliente.codiceFiscale"))
		{
			Join<Fattura, Cliente> clienteJoin = root.join("cliente");
			
			String finalColumn = "codiceFiscale";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		if (name.equalsIgnoreCase("cliente.partitaIva"))
		{
			Join<Fattura, Cliente> clienteJoin = root.join("cliente");
			
			String finalColumn = "partitaIva";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		if (name.equalsIgnoreCase("cliente.ragioneSociale"))
		{
			Join<Fattura, Cliente> clienteJoin = root.join("cliente");
			
			String finalColumn = "ragioneSociale";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(clienteJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(clienteJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(clienteJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
		// UTENTI
		
		if (name.equalsIgnoreCase("ruoloUtente.name"))
		{
			Join<User, RoleUser> ruoloJoin = root.join("ruoloUtente");
			
			String finalColumn = "name";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(ruoloJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(ruoloJoin.<String>get(finalColumn), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(ruoloJoin.<String>get(finalColumn), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(ruoloJoin.<String>get(finalColumn), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(ruoloJoin.<String>get(finalColumn), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(ruoloJoin.<String>get(finalColumn), "%" + value + "%");
			}
		}
		
//		// RUOLO - WORKFLOW
//		
//		if (name.equalsIgnoreCase("ruoloWorkflowStep.name"))
//		{
//			Join<WorkflowStepRole, RoleUser> workflowStepJoin = root.join("ruoloWorkflowStep");
//			
//			String finalColumn = "name";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//		
//		if (name.equalsIgnoreCase("workflowStepRuolo.nomeStep"))
//		{
//			Join<WorkflowStepRole, WorkflowStep> workflowStepJoin = root.join("workflowStepRuolo");
//			
//			String finalColumn = "nomeStep";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//
//		if (name.equalsIgnoreCase("workflowStepRuolo.workflow.business"))
//		{
//			Join<WorkflowStepRole, WorkflowStep> workflowStepJoin = root.join("workflowStepRuolo");
//			Join<WorkflowStepRole, Workflow> workflowJoin = workflowStepJoin.join("workflow");
//			
//			String finalColumn = "business";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(workflowJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(workflowJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(workflowJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//		
//		// NOTIFICHE
//		
//		if (name.equalsIgnoreCase("tipologiaNotifica.nome"))
//		{
//			Join<Notifica, NotificaTipologia> tipologiaJoin = root.join("tipologiaNotifica");
//			
//			String finalColumn = "nome";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(tipologiaJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(tipologiaJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(tipologiaJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(tipologiaJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(tipologiaJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(tipologiaJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//		
//		if (name.equalsIgnoreCase("workflowStepNotifiche.nomeStep"))
//		{
//			Join<Notifica, WorkflowStep> workflowStepJoin = root.join("workflowStepNotifiche");
//			
//			String finalColumn = "nomeStep";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(workflowStepJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(workflowStepJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(workflowStepJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//
//		if (name.equalsIgnoreCase("workflowStepNotifiche.workflow.business"))
//		{
//			Join<Notifica, WorkflowStep> workflowStepJoin = root.join("workflowStepNotifiche");
//			Join<Notifica, Workflow> workflowJoin = workflowStepJoin.join("workflow");
//			
//			String finalColumn = "business";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(workflowJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(workflowJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(workflowJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(workflowJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}
//		
//		// SALE - TIR
//		
//		if (name.equalsIgnoreCase("saleTir.richiestaId"))
//		{
//			Join<SaleTirRichiesta, RichiestaCliente> saleTirRichiestaJoin = root.join("richiestaSaleTir");
//			
//			String finalColumn = "id";
//			
//			if (operator.equalsIgnoreCase("eq")) {
//				return builder.equal(saleTirRichiestaJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("ne")) {
//				return builder.notEqual(saleTirRichiestaJoin.<String>get(finalColumn), value);
//			} else if (operator.equalsIgnoreCase("lke")) {
//				return builder.like(saleTirRichiestaJoin.<String>get(finalColumn), "%" + value + "%");
//			} else if (operator.equalsIgnoreCase("elke")) {
//				return builder.like(saleTirRichiestaJoin.<String>get(finalColumn), "%" + value);
//			} else if (operator.equalsIgnoreCase("slke")) {
//				return builder.like(saleTirRichiestaJoin.<String>get(finalColumn), value + "%");
//			} else if (operator.equalsIgnoreCase("nlke")) {
//				return builder.notLike(saleTirRichiestaJoin.<String>get(finalColumn), "%" + value + "%");
//			}
//		}

		/*if (name.equalsIgnoreCase("owedays.dealerCode")) {
			/// Se sono dealer passo questo filtro e ritorno solamente le giornate ODIERNE
			/// relative al dealer passato
			if (operator.equalsIgnoreCase("eqone")) {
				Join<OpenWeekendDays, DealerVSOwe> daysDealer = root.join("dealer");

				return builder.and(builder.equal(daysDealer.get("code"), value),
						builder.equal(root.<Date>get("date"), java.util.Date.from(LocalDate
								.now(ZoneId.of("Europe/Rome")).atStartOfDay(ZoneId.of("Europe/Rome")).toInstant())));
			}
			else {
				Join<OpenWeekendDays, DealerVSOwe> daysDealer = root.join("dealer");
				return daysDealer.get("code").in(valueList);
			}
		}*/
		
		///Se sono sulla vista degli open days, e filtro per dealerName, devo fare una join per recuperare il valore
		/*if(root.getJavaType().equals(OpenWeekendDays.class) && name.equals("dealerName")) {
			Join<OpenWeekendDays, DealerVSOwe> daysDealer = root.join("dealer");
			
			name = "label";
			
			if (operator.equalsIgnoreCase("eq")) {
				return builder.equal(daysDealer.<String>get(name), value);
			} else if (operator.equalsIgnoreCase("ne")) {
				return builder.notEqual(daysDealer.<String>get(name), value);
			} else if (operator.equalsIgnoreCase("lke")) {
				return builder.like(daysDealer.<String>get(name), "%" + value + "%");
			} else if (operator.equalsIgnoreCase("elke")) {
				return builder.like(daysDealer.<String>get(name), "%" + value);
			} else if (operator.equalsIgnoreCase("slke")) {
				return builder.like(daysDealer.<String>get(name), value + "%");
			} else if (operator.equalsIgnoreCase("nlke")) {
				return builder.notLike(daysDealer.<String>get(name), "%" + value + "%");
			}
		}*/

		if (operator.equalsIgnoreCase("lt")) {
			Date result = null;
			try {
				if(name.equalsIgnoreCase("dataValidita")){
					result = new Date();
				}
				else
					result = df.parse(value);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return builder.lessThan(root.<Date>get(name), result);
		} else if (operator.equalsIgnoreCase("lte")) {
			Date result = null;
			try {
				if(name.equalsIgnoreCase("dataValidita")){
					result = new Date();
				}
				else
					result = df.parse(value);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return builder.lessThanOrEqualTo(root.<Date>get(name), result);
		} else if (operator.equalsIgnoreCase("gt")) {
			Date result = null;
			try {
				if(name.equalsIgnoreCase("dataValidita")){
					result = new Date();
				}
				else
					result = df.parse(value);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return builder.greaterThan(root.<Date>get(name), result);
		} else if (operator.equalsIgnoreCase("gte")) {
			Date result = null;
			try {
				if(name.equalsIgnoreCase("dataValidita")){
					result = new Date();
				}
				else
					result = df.parse(value);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return builder.greaterThanOrEqualTo(root.<Date>get(name), result);
		} else if (operator.equalsIgnoreCase("eq")) {
			return builder.equal(root.<String>get(name), value);
		} else if (operator.equalsIgnoreCase("ne")) {
			return builder.notEqual(root.<String>get(name), value);
		} else if (operator.equalsIgnoreCase("lke")) {
			return builder.like(root.<String>get(name), "%" + value + "%");
		} else if (operator.equalsIgnoreCase("elke")) {
			return builder.like(root.<String>get(name), "%" + value);
		} else if (operator.equalsIgnoreCase("slke")) {
			return builder.like(root.<String>get(name), value + "%");
		} else if (operator.equalsIgnoreCase("nlke")) {
			return builder.notLike(root.<String>get(name), "%" + value + "%");
		} else if (operator.equalsIgnoreCase("in")) {
			return root.<String>get(name).in(valueList);
		} else if (operator.equalsIgnoreCase("eqbool")) {
			return builder.equal(root.<Boolean>get(name), "1".equalsIgnoreCase(value));
		}

		return null;
	}

}