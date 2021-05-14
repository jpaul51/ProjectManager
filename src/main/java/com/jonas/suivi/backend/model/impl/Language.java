package com.jonas.suivi.backend.model.impl;

import java.util.Locale;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.Name;

public class Language implements Displayable{

	
	@Name("locale")
	Locale languageLocale;
	
	@Id @GeneratedValue
	Long id;
	
	
	

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}






	public void setId(Long id) {
		this.id = id;
	}






	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
