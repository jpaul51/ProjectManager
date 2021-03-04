package com.jonas.suivi.views.model;

import java.util.Arrays;
import java.util.List;

public class FieldDetailList extends FieldDetail {

	List<Class<?  extends Enum>> valueProviders;
	boolean unique = true;
	

	public FieldDetailList() {
		super();
		this.setType(Input.SELECT);
	}


	public List<Class<? extends Enum>> getValueProviders() {
		return valueProviders;
	}

	public void setValueProviders(Class<? extends Enum>... valueProviders) {
		this.valueProviders = Arrays.asList(valueProviders);
	}
	
	public void setUnique(boolean pUnique) {
		this.unique = pUnique;
	}


	public boolean isUnique() {
		return unique;
	}
	
	
	
}
