package com.company.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.model.Role;
import com.company.app.model.Usuario;
import com.company.app.repository.UsuarioRepository;

@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService {

	private Logger log=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user=userRepo.findByUsername(username);
		
		if(user == null) {
			log.error("Error. El usuario no existe");
			throw new UsernameNotFoundException("Username: "+username+" no existe");
		}
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName_role()));
		}
		
		if(authorities.isEmpty()) {
			log.error("El usuario "+username+" no tiene roles");
			throw new UsernameNotFoundException("Username: "+username+" no tiene roles");
		}
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
	
	

}
