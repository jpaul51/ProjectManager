package com.jonas.suivi.backend.model.impl;

import java.util.Locale;

import javax.persistence.Id;

import com.jonas.suivi.backend.model.AbstractEntity;
import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.Name;

public class Language  extends AbstractEntity implements Displayable{

	
	@Name("locale")
	Locale languageLocale;
	
	
	
	
	

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
