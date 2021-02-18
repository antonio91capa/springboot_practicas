package com.company.app.dao;

import java.util.List;

import com.company.app.model.Cliente;

public interface ClienteDAO {

	public void save(Cliente cliente);
	public Cliente findByIdCliente(Long idCliente);
	public List<Cliente> allClientes();
	public void delete(Long idCliente);
}
