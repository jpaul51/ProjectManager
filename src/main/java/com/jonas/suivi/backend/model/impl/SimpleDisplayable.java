package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;

import javax.persistence.Id;

import com.jonas.suivi.backend.model.Displayable;

public class SimpleDisplayable   implements Serializable, Displayable {

	
	@Id
	String value;
	
	public SimpleDisplayable(String pvalue) {
		this.value = pvalue;
	}
	
	@Override
	public void setSimpleValue(String value) {
		this.setValue(value);
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
	
	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		SimpleDisplayable other = (SimpleDisplayable) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
