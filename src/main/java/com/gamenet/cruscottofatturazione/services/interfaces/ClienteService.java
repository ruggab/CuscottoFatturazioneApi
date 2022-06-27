package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.ClienteCruscotto;

public interface ClienteService {

	List<ClienteCruscotto> getClienti();
	ClienteCruscotto getClienteById(Integer clienteId);
	Boolean saveCliente(ClienteCruscotto cliente, String utenteUpdate);
	Boolean deleteCliente(Integer clienteId, String utenteUpdate);

	

}