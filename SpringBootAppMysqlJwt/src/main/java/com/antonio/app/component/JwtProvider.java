package com.antonio.app.component;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.antonio.app.controller.ProductController;
import com.antonio.app.security.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	public final static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		UserPrincipal userPrinciapl = (UserPrincipal) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject(userPrinciapl.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Malformed Token " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported token " + e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("Expired token " + e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Illegal Argument: " + e.getMessage());
		} catch (SignatureException e) {
			log.error("Sign Error: " + e.getMessage());
		}
		return false;
	}

}
