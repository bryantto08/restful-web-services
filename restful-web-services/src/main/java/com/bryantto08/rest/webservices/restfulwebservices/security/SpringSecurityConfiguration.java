package com.bryantto08.rest.webservices.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class SpringSecurityConfiguration {  // Creating our own authentication class
	
	@Bean  // Method will return a Bean that is processed by Spring
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 1) All requests should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		// 2) If a request is not authenticated, a login page is shown
		http.httpBasic(withDefaults());
	
		//3) CSRF -> Post, Put
		http.csrf().disable();
		
		return http.build();
	}

}
