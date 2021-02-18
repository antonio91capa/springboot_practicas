package com.company.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashManager=new SessionFlashMapManager();
		FlashMap flashMap=new FlashMap();
		
		flashMap.put("success", authentication.getName()+" ha iniciado sesion con exito!");
		flashManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info("El usuario "+authentication.getName()+" ha iniciado sesion correctamente");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
