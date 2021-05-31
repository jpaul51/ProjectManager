package com.jonas.suivi.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jonas.suivi.backend.model.impl.UserAccount;
import com.jonas.suivi.backend.services.UserAccountService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserAccountService userAccountService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserAccount usrAcc = userAccountService.getByLogin(username);

		if (username.equals("admin") && usrAcc == null) {
			userAccountService.initialize();
			usrAcc = userAccountService.getByLogin(username);
		}

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		if (usrAcc != null && usrAcc.getLogin().equals(username) && encoder.matches(password, usrAcc.getPassword())) {
			final String pushId = UUID.randomUUID().toString();

			
			List<GrantedAuthority> grants = new ArrayList<>();
			grants.add(new GrantedAuthority() {
				
				@Override
				public String getAuthority() {
					return "admin";
				}
			});
			
			UserDetails userDetails = new User(usrAcc.getLogin(), usrAcc.getPassword(), grants);
			final String token = jwtTokenUtil.generateToken(userDetails);

			return new UsernamePasswordAuthenticationToken(username, token, Collections.emptyList());
		} else {
			throw new BadCredentialsException("Authentication failed");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.equals(UsernamePasswordAuthenticationToken.class);
	}
}