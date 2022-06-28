package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import com.gamenet.cruscottofatturazione.entities.Cliente;

public interface ClienteService {

	List<Cliente> getClienti();
	Cliente getClienteById(Integer clienteId);
	Boolean saveCliente(Cliente cliente, String utenteUpdate);
	Boolean deleteCliente(Integer clienteId, String utenteUpdate);

	

}