package com.company.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.app.auth.filter.JWTAuthenticationFilter;
import com.company.app.auth.filter.JWTAuthorizationFilter;
import com.company.app.auth.service.JWTService;
import com.company.app.service.UserServiceImpl;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private JWTService jwtService;
	
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
//		PasswordEncoder encoder=passwordEncoder;
//		UserBuilder users=User.builder().passwordEncoder(encoder::encode);
//		
//		builder.inMemoryAuthentication()
//		.withUser(users.username("admin").password("admin123").roles("ADMIN", "USER"))
//		.withUser(users.username("antonio").password("12345").roles("USER"));
		
		
//		builder.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder)
//		.usersByUsernameQuery("select username, password, enabled from users u where username=?")
//		.authoritiesByUsernameQuery("select u.username, r.name_rol from rol r inner join users u on (r.user_id=u.id) where u.username=?");
		
		builder.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	/*
	 * Configuracion de seguridad con Spring Security Normal
	 */
	/* @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale", "/api/**").permitAll()
		.antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.antMatchers("/productos/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	*/
	
	// Configuracion de seguridad con JWT(JSON Web Token)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale").permitAll()
		.anyRequest().authenticated()
		/* .and()
			.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
			.permitAll()
		.and()
			.logout().permitAll()()
		.and()
			.exceptionHandling().accessDeniedPage("/error_403")
		*/
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
}
