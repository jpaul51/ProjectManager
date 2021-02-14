package com.jonas.suivi.views.components;

import com.vaadin.flow.component.Component;

@com.vaadin.flow.component.Tag(value = "SuperComponent")
public abstract class AbstractSimpleSuperComponent<V> extends AbstractSuperComponent implements SuperComponentInterface<V, Component>{

	@Override
	public abstract void setValue(V value);
		
	

	

}
