package com.jonas.suivi.backend.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.jonas.suivi.backend.model.AbstractEntity;

@Entity
public class UserAccount extends AbstractEntity{

	@Column(unique = true)
	private String login;
	private String password;
	@Column
	private boolean resetPassword = false;
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}
	
	
	
	
}
