package com.jonas.suivi.views.components;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.customfield.CustomField;

@com.vaadin.flow.component.Tag(value = "SuperComponent")
public abstract class AbstractSuperCustomField extends AbstractSuperComponent<Object> implements HasComponents, HasText, SuperComponentInterface<String, CustomField<String>>{

//	public abstract void setValue(Object value);
	
}
