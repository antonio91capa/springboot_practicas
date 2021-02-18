package com.antonio.app.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.app.component.JwtProvider;
import com.antonio.app.dto.JwtDTO;
import com.antonio.app.dto.LoginUser;
import com.antonio.app.dto.Mensajes;
import com.antonio.app.dto.UsuarioDTO;
import com.antonio.app.entity.Role;
import com.antonio.app.entity.Usuario;
import com.antonio.app.enums.RoleEnum;
import com.antonio.app.service.RoleService;
import com.antonio.app.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RoleService roleService;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/new")
	public ResponseEntity<?> nuevo(@Valid @RequestBody UsuarioDTO user, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity(new Mensajes("Empty fields or email invalid"), HttpStatus.BAD_REQUEST);
		if (usuarioService.existsByName(user.getUsername()))
			return new ResponseEntity(new Mensajes("This username already exists"), HttpStatus.BAD_REQUEST);
		if (usuarioService.existsByEmail(user.getEmail()))
			return new ResponseEntity(new Mensajes("This email already exists"), HttpStatus.BAD_REQUEST);

		Usuario usuario = new Usuario(user.getNombre(), user.getUsername(), encoder.encode(user.getPassword()),
				user.getEmail());
		Set<String> roles = user.getRoles();
		Set<Role> roleUser = new HashSet<>();
		for (String rol : roles) {
			switch (rol) {
			case "admin":
				Role rolAdmin = roleService.getByRole(RoleEnum.ROLE_ADMIN).get();
				roleUser.add(rolAdmin);
				break;

			default:
				Role rolUser = roleService.getByRole(RoleEnum.ROLE_USER).get();
				roleUser.add(rolUser);
				break;
			}
		}

		usuario.setRoles(roleUser);
		usuarioService.save(usuario);

		return new ResponseEntity(new Mensajes("User saved succesfully"), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity(new Mensajes("Empty fields or email invalid"), HttpStatus.BAD_REQUEST);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<JwtDTO>(jwtDTO, HttpStatus.OK);
	}

}
