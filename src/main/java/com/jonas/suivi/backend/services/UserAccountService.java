package com.jonas.suivi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.impl.UserAccount;
import com.jonas.suivi.backend.repository.UserAccountRepository;

@Service
public class UserAccountService {

	
	@Autowired
	UserAccountRepository usrAccountRepo;
	
	
	
	public UserAccount getByLogin(String login) {
		return usrAccountRepo.findByLogin(login);
	}
	
	public void updatePassword(String password) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		UserAccount usr = getUsrLoggedAccount();
		usr.setPassword(encoder.encode(password));
		usr.setResetPassword(false);
		usrAccountRepo.save(usr);
	}

	public void initialize() {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserAccount admin = new UserAccount();
		admin.setLogin("admin");
		admin.setPassword(encoder.encode("admin"));
		admin.setResetPassword(true);
		usrAccountRepo.save(admin);
	}
	public UserAccount getUsrLoggedAccount() { 
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	       return getByLogin(authentication.getName());
	        
	        
	    }
	
	
	
}
