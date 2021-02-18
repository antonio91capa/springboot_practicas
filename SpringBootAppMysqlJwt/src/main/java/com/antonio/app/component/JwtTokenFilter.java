/*
 Esta clase lo que hace es obtener el token y el usuario, 
 este último a partir del nombre de usuario y comprueba si es la autenticación es correcta. 
 En caso afirmativo la añade al contexto y, por último, se añade el filtro.

El token tiene la forma de la cadena Bearer xxxxx.yyyyy.zzzz. 
Con getToken() lo que hacemos es eliminar el inicio “Bearer “.
 * */
package com.antonio.app.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.antonio.app.controller.ProductController;
import com.antonio.app.service.UserDetailsServiceImpl;

public class JwtTokenFilter extends OncePerRequestFilter {

	public final static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			if (token != null && jwtProvider.validateToken(token)) {
				log.info("Validate Token");
				String username = jwtProvider.getUsernameFromToken(token);
				log.info("username: "+username);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				log.info("userDetails username: "+userDetails.getUsername());
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				
				log.info("authentication Token: "+auth.getName());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			log.error("Failed doFilter "+e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if (auth != null && auth.startsWith("Bearer "))
			return auth.replace("Bearer ", "");

		return null;
	}

}
