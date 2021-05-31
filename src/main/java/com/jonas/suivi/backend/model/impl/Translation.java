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

	private static final long serialVersionUID = -4404844143349966021L;
	
	@Id
	@GeneratedValue
	Long id;
	@Convert(converter = SimpleDisplayableConverter.class)
	SimpleDisplayable key;
	String frenchValue;
	String englishValue;

//	HashMap<Locale,String> transLationByLocale;

	@Override
	public void setSimpleValue(String value) {
		this.setKey(new SimpleDisplayable(value));
	}

	public String getTranslationByLocal(Locale loc) {
		String ret = null;

		if (loc.equals(ESupportedLocales.FRANCE.getLocale()))
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
	public Integer getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		return key.getLabel();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Translation other = (Translation) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
