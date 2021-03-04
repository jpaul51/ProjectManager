package com.jonas.suivi.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.UserAccount;
import com.jonas.suivi.backend.repository.UserAccountRepository;

@Service("Account")
public class UserAccountService implements DisplayableService{

	
	@Autowired
	UserAccountRepository usrAccountRepo;
	
	
	
	public UserAccount getByLogin(String login) {
		return usrAccountRepo.findByLogin(login);
	}
	
	public void updatePassword(String password) {
		
		UserAccount usr = getUsrLoggedAccount();
		encodePassword(password, usr);
		usr.setResetPassword(false);
		usrAccountRepo.save(usr);
	}

	private void encodePassword(String password, UserAccount usr) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		usr.setPassword(encoder.encode(password));
	}

	public void initialize() {

		
		UserAccount admin = new UserAccount();
		admin.setLogin("admin");
		encodePassword("admin",admin);
		admin.setResetPassword(true);
		usrAccountRepo.save(admin);
	}
	public UserAccount getUsrLoggedAccount() { 
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	       return getByLogin(authentication.getName());
	        
	        
	    }

	@Override
	public <T extends Displayable> List<T> getAll() {
		return (List<T>) usrAccountRepo.findAll();
	}

	@Override
	public <T extends Displayable> void create(T d) {
		UserAccount usrAcc = (UserAccount) d;
		encodePassword(usrAcc.getPassword(), usrAcc);
		usrAccountRepo.save(usrAcc);		
	}
	
	@Override
	public <T extends Displayable> void update(T d) {
		usrAccountRepo.save((UserAccount) d);		
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		usrAccountRepo.delete((UserAccount) d);
	}

	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		
		UserAccount t = new UserAccount();
		t.setLogin(d);
		
		Example<UserAccount> personExample = (Example<UserAccount>) Example.of(t);

		return (Optional<T>) getRepo().findOne((Example<UserAccount>) personExample);
		
	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		// TODO Auto-generated method stub
		return (JpaRepository<T, Long>) usrAccountRepo;
	}
	
	
	
}
