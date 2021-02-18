package com.company.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.app.model.Cliente;
import com.company.app.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteApiController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value="/listar")
	@Secured("ROLE_ADMIN")
	@ResponseBody
	public List<Cliente> listApiRest(Model model) {
		return clienteService.findAll();
	}

}
