package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;

import javax.persistence.Id;

import com.jonas.suivi.backend.model.AbstractEntity;
import com.jonas.suivi.backend.model.Displayable;

public class SimpleDisplayable extends AbstractEntity  implements Serializable, Displayable {

	
	@Id
	String value;
	
	public SimpleDisplayable(String pvalue) {
		this.value = pvalue;
	}
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		return value;
	}
	
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
	

}
