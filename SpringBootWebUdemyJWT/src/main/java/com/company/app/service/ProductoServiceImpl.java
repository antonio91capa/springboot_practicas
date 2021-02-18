package com.company.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.model.Producto;
import com.company.app.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepo;
	
	@Override
	@Transactional(readOnly=true)
	public List<Producto> allProductos() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoRepo.findAll();
	}

	
}
