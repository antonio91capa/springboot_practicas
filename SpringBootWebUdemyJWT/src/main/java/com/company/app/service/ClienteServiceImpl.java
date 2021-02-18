package com.company.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.dao.ClienteDAO;
import com.company.app.model.Cliente;
import com.company.app.model.Factura;
import com.company.app.model.Producto;
import com.company.app.repository.ClienteRepository;
import com.company.app.repository.FacturaRepository;
import com.company.app.repository.ProductoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

//	@Autowired
//	private ClienteDAO clienteDAO;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private ProductoRepository productoRepo;
	
	@Autowired
	private FacturaRepository facturaRepo;
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteRepo.save(cliente);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long idCliente) {
		// TODO Auto-generated method stub
		return clienteRepo.findById(idCliente).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteRepo.findAll();
	}

	@Override
	@Transactional
	public void delete(Long idCliente) {
		// TODO Auto-generated method stub
		clienteRepo.deleteById(idCliente);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clienteRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByName(String term) {
		// TODO Auto-generated method stub
		return productoRepo.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		facturaRepo.save(factura);
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long idProducto) {
		// TODO Auto-generated method stub
		return productoRepo.findById(idProducto).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long idFactura) {
		// TODO Auto-generated method stub
		return facturaRepo.findById(idFactura).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long idFactura) {
		// TODO Auto-generated method stub
		facturaRepo.deleteById(idFactura);
	}

	@Override
	@Transactional(readOnly=true)
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
		// TODO Auto-generated method stub
		return facturaRepo.fetchByIdWithClienteWithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findByIdWithFacturas(Long id) {
		// TODO Auto-generated method stub
		return clienteRepo.fetchByIdWithFacturas(id);
	}

}
