package com.company.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.app.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

}
