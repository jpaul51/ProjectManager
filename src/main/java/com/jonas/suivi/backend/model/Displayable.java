package com.jonas.suivi.backend.model;

import java.io.Serializable;


public interface Displayable extends Serializable{

	
	
	public String getLabel();
	
	
	
//	public void setValue();
	
	public default  String getClazz() {
		return Displayable.class.getCanonicalName();
	}
	
	
	
	
//	@Transient
//	public default List<Method> getMethodList() {
//		return Arrays.asList(getClass().getDeclaredMethods());
//	}
//	public ValueProvider<T,?> getPropertyValue( ValueProvider<? extends T,?> provider);

//	ValueProvider<? extends Serializable, ?> getPropertyValue(ValueProvider<?, ?> provider);
	
}
