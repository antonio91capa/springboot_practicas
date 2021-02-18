package com.company.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.company.app.model.Producto;
import com.company.app.service.ProductoService;

@Controller
@RequestMapping(value="/productos", method=RequestMethod.GET)
@Secured("ROLE_ADMIN")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/listar")
	public ModelAndView allProductos() {
		List<Producto> productos=productoService.allProductos();
		
		ModelAndView mav=new ModelAndView("producto/listar", "titulo", "Listado de productos");
		mav.addObject("productos", productos);
		return mav;
	}
}
