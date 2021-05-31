package com.jonas.suivi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jonas.suivi.backend.model.impl.UserAccount;
import com.jonas.suivi.security.JwtTokenUtil;
import com.vaadin.flow.server.startup.ApplicationConfiguration;
//import com.javainuse.service.JwtUserDetailsService;

@Controller
public class LogoutController {

	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
	public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
//	        for(javax.servlet.http.Cookie cookie : request.getCookies()) {
//	            cookie.setMaxAge(0);
//	        }

		return "logout";
	}

	@RequestMapping(value = { "/loginRemote" }, method = RequestMethod.POST)
	public @ResponseBody Object login(@RequestBody UserAccount loginDetail, HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession(true);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDetail.getLogin(), loginDetail.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		Token t = new Token((String) authentication.getCredentials());
		t.setUser(loginDetail.getLogin());

		return t;
	}

}
