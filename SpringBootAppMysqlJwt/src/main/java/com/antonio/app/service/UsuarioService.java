package com.antonio.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.app.entity.Usuario;
import com.antonio.app.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Optional<Usuario> getByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public boolean existsByName(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	public void save(Usuario user) {
		usuarioRepository.save(user);
	}

}
