package com.antonio.app.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.antonio.app.controller.ProductController;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
	
	public final static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Fail method commence");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credentials errors");
	}

}
