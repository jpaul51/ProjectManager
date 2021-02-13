package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.SimpleDisplayableConverter;
import com.jonas.suivi.views.descriptors.ESupportedLocales;


@Entity
public class Translation implements Serializable, Displayable {

	@Id
	@GeneratedValue
	Long id;
    @Convert(converter = SimpleDisplayableConverter.class)
	SimpleDisplayable key;
	String frenchValue;
	String englishValue;
	
//	HashMap<Locale,String> transLationByLocale;
	
	
	
	public String getTranslationByLocal(Locale loc) {
		String ret = null;
		
		if(loc.equals(ESupportedLocales.FRANCE.getLocale()))
			ret = frenchValue;
		else
			ret = englishValue;
		
		return ret;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	



	public SimpleDisplayable getKey() {
		return key;
	}


	public void setKey(SimpleDisplayable key) {
		this.key = key;
	}


	public String getFrenchValue() {
		return frenchValue;
	}
	public void setFrenchValue(String frenchValue) {
		this.frenchValue = frenchValue;
	}
	public String getEnglishValue() {
		return englishValue;
	}
	public void setEnglishValue(String englishValue) {
		this.englishValue = englishValue;
	}
	
	
	@Override
	public String getLabel() {
		return key.getLabel();
	}
	


	
	
	
	
	
}
