package com.jonas.suivi.views.components;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class TextFieldComponent extends AbstractSimpleSuperComponent<String>{

	Component component;
	boolean showValue = true;
	
	public TextFieldComponent(FieldDetail field, String label) {
		
		if(field.isValueHidden()) {
			component = new PasswordField(label);

		}
		component = new TextField(label);
	}
	
	@Override
	public Component getComponent() {
		return component;
	}

	

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return ((HasValue<ValueChangeEvent<String>,String>) component).getValue();
	}

	@Override
	public void setValue(String value) {
		if(value == null)
			value = "";
		
		((HasValue<ValueChangeEvent<String>,String>) component).setValue(value);
		
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<String>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		((HasValue)component).setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return ((HasValue)component).isReadOnly();
	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRequiredIndicatorVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
