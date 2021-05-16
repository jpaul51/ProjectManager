package com.jonas.suivi.backend.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class UserAccount implements Displayable{
	
	@Id @GeneratedValue
	Long id;
	@Column(unique = true) 
	private String login;
	private String password;
	@Column
	private boolean resetPassword = false;
	
	
	@Override
	public void setSimpleValue(String value) {
		this.login = value;
	}
	
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
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return login;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
}
