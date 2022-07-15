package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.response.ClienteAutoComplete;
import com.gamenet.cruscottofatturazione.models.response.ClientiListOverview;
import com.gamenet.cruscottofatturazione.models.response.SaveResponse;

public interface ClienteService {

	List<Cliente> getClienti();
	Cliente getClienteById(String codiceCliente);
	SaveResponse saveCliente(Cliente cliente, String utenteUpdate);
	SaveResponse updateCliente(Cliente cliente, String utenteUpdate);
	Boolean deleteCliente(String codiceCliente, String utenteUpdate);
	List<Cliente> getClienti(String codiceSocieta);
	ClientiListOverview getClientiDataTable(JsonNode payload);
	List<ClienteAutoComplete> getActiveClienti(String codiceSocieta);

	

}