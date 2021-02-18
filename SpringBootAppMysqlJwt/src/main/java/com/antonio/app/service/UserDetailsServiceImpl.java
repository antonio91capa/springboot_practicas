package com.antonio.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.app.entity.Usuario;
import com.antonio.app.security.UserPrincipal;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	public final static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioService.getByUsername(username).get();
		log.info("username en Service: "+user.getUsername());
		return UserPrincipal.build(user);
	}

}
