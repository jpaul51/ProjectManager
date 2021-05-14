package com.jonas.suivi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.jonas.suivi.backend.services.UserAccountService;
import com.jonas.suivi.security.CustomAuthenticationProvider;

@Configuration
public class Config {

	@Autowired
	UserAccountService usrAccountService;
	
	@Autowired
	CustomAuthenticationProvider authProvider;

	@Bean
	public AuthenticationManager authenticationManagerBean() {
		return new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return authProvider.authenticate(authentication);
				
			}
		};
	}

}
