package com.antonio.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.app.dto.Mensajes;
import com.antonio.app.entity.Product;
import com.antonio.app.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	public final static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@GetMapping("/lista")
	public ResponseEntity<List<Product>> getLista() {
		List<Product> products = productService.findAll();
		
		log.info("Listing products...");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<Product> getDetalle(@PathVariable Long id) {
		if (!productService.existsById(id))
			return new ResponseEntity(new Mensajes("Product Not Exists"), HttpStatus.NOT_FOUND);

		Product product = productService.findById(id).get();
		log.info("Product found successfully");
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("/new")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> save(@RequestBody Product product) {
		if (StringUtils.isEmpty(product.getName()))
			return new ResponseEntity(new Mensajes("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if ((Integer) product.getPrecio() == null || product.getPrecio() == 0)
			return new ResponseEntity(new Mensajes("El precio es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productService.existsByName(product.getName()))
			return new ResponseEntity(new Mensajes("El nombre ya existe"), HttpStatus.BAD_REQUEST);

		productService.save(product);
		log.info("Product saved successfully");
		return new ResponseEntity(new Mensajes("Product saved successfully"), HttpStatus.CREATED);
	}

	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> save(@RequestBody Product product, @PathVariable("id") Long id) {
		if (!productService.existsById(id))
			return new ResponseEntity(new Mensajes("Product not exists"), HttpStatus.NOT_FOUND);
		
		if (StringUtils.isEmpty(product.getName()))
			return new ResponseEntity(new Mensajes("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		
		if ((Integer) product.getPrecio() == null || product.getPrecio() == 0)
			return new ResponseEntity(new Mensajes("El precio es obligatorio"), HttpStatus.BAD_REQUEST);
		
		if (productService.existsByName(product.getName())
				&& productService.findByName(product.getName()).get().getId() != id)
			return new ResponseEntity(new Mensajes("El nombre ya existe"), HttpStatus.BAD_REQUEST);

		Product newProduct = productService.findById(id).get();
		log.info("new Product: "+newProduct);
		newProduct.setName(product.getName());
		newProduct.setPrecio(product.getPrecio());
		
		productService.save(product);
		log.info("Product updated successfully");
		return new ResponseEntity(new Mensajes("Product updated successfully"), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!productService.existsById(id))
			return new ResponseEntity(new Mensajes("Product not Found"), HttpStatus.NOT_FOUND);

		productService.borrar(id);
		log.info("Product deleted successfully");
		return new ResponseEntity(new Mensajes("Product deleted successfully"), HttpStatus.OK);
	}

}
