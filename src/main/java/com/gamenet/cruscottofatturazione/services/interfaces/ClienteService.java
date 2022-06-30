package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;

public interface ClienteService {

	List<Cliente> getClienti();
	Cliente getClienteById(Integer clienteId);
	Boolean saveCliente(Cliente cliente, String utenteUpdate);
	Boolean deleteCliente(Integer clienteId, String utenteUpdate);
	List<Cliente> getClienti(String codiceSocieta);
	ClientiListOverview getClientiDataTable(JsonNode payload);

	

}