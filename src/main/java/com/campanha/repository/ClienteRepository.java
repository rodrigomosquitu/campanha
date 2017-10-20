package com.campanha.repository;

import java.util.List;

import com.campanha.domain.Cliente;

public interface ClienteRepository {

	public void saveCliente(Cliente tree);

	public Cliente getCliente(int id);
	
	public Cliente getClienteByEmail(String email);
	
	public List<Cliente> getAllClientes();
	
	public Cliente associaClientesCampanhas(int idCliente);
	
	public void createCollection();

	public void dropCollection();
}
