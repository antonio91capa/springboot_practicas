package com.company.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoritiesMixin {

	
//	@JsonCreator
//	public SimpleGrantedAuthoritiesMixin(@JsonProperty("authority") String role) {
//	}
	
	@JsonCreator
	public SimpleGrantedAuthoritiesMixin(@JsonProperty("name_role") String role) {
	}

	
}
