package com.antonio.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.app.entity.Product;
import com.antonio.app.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	public Optional<Product> findByName(String name) {
		return productRepository.findByName(name);
	}

	public void save(Product product) {
		productRepository.save(product);
	}

	public void borrar(Long id) {
		productRepository.deleteById(id);
	}

	public boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}

	public boolean existsById(Long id) {
		return productRepository.existsById(id);
	}

}
