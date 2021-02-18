package com.antonio.app.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.antonio.app.entity.Usuario;
import com.antonio.app.service.UserDetailsServiceImpl;

public class UserPrincipal implements UserDetails {
	
	public final static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	private String username;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String nombre, String username, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}
	
	public static UserPrincipal build(Usuario usuario) {
		log.info("build principal");
		List<GrantedAuthority> authorities=usuario.getRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getRole().name()))
				.collect(Collectors.toList());
		
		log.info("authorities: "+authorities.size());
		
		return new UserPrincipal(usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getPassword(), usuario.getEmail(), authorities);
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
