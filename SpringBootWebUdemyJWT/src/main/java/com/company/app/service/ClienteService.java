package com.company.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.company.app.model.Cliente;
import com.company.app.model.Factura;
import com.company.app.model.Producto;

public interface ClienteService {

	public void save(Cliente cliente);
	public Cliente findOne(Long idCliente);
	public Cliente findByIdWithFacturas(Long id);
	public void delete(Long idCliente);
	
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	
	public List<Producto> findByName(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long idProducto);
	
	public Factura findFacturaById(Long idFactura);
	public void deleteFactura(Long idFactura);
	
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
