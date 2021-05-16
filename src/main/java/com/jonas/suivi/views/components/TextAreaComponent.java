package com.jonas.suivi.views.components;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;

public class TextAreaComponent extends AbstractSimpleSuperComponent<String> {

	
	TextArea component;
	
	public TextAreaComponent(FieldDetail field) {
		super();
		component = new TextArea();
		
	}
	
	public TextAreaComponent(FieldDetail field,String label) {
		this(field);
		component.setLabel(label);		
	}
	
	@Override
	public TextArea getComponent() {

		return component;
	}
	@Override
	public Component getContainer() {
		return component;
	}

	@Override
	public String getValue() {
		return component.getValue();
	}

	@Override
	public void setValue(String value) {
		if(value == null)
			value="";
		component.setValue(value);	

		
	}
	
	@Override
	public Registration addValueChangeListener(ValueChangeListener listener) {
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		component.setReadOnly(readOnly);		
	}


	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		isRequired = true;
		component.setReadOnly(requiredIndicatorVisible);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
