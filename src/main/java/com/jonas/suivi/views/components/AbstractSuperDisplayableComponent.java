package com.jonas.suivi.views.components;

import com.jonas.suivi.backend.model.Displayable;
import com.vaadin.flow.component.Component;

public abstract class AbstractSuperDisplayableComponent<U extends Displayable> extends AbstractSuperComponent 
implements SuperComponentInterface<U, Component>{

	public abstract void addValue(U val);
	public abstract void editValue(U val);
	
	
}	
