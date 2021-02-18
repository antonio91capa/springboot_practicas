package com.company.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;

//import java.nio.file.Paths;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	private final Logger log=LoggerFactory.getLogger(getClass());

	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath=Paths.get("upload").toAbsolutePath().toUri().toString();
		log.info(resourcePath);
		registry.addResourceHandler("/upload/**")
		.addResourceLocations(resourcePath);
//		.addResourceLocations("file:/C://tmp//upload//");
	}*/
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error403");
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localResolver=new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale("es", "ES"));
		return localResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localChangeInterceptor() {
		LocaleChangeInterceptor localInterceptor=new LocaleChangeInterceptor();
		localInterceptor.setParamName("lang");
		return localInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localChangeInterceptor());
	}
	
	//Configuracion para convertir un objeto a xml
	@Bean
	public Jaxb2Marshaller jaxb2Mashaller() {
		Jaxb2Marshaller marshaller=new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {com.company.app.view.xml.ClienteList.class});
		return marshaller;
	}
}
