package com.jonas.suivi.views.components;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class LabelComponent extends AbstractSimpleSuperComponent<String> {

	
	TextField component;
	
	public  LabelComponent(FieldDetail field, String text) {
		component = new TextField(text);
		component.setReadOnly(true);
	}

	@Override
	public TextField getComponent() {
		// TODO Auto-generated method stub
		return component;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return component.getValue();
	}



	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
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
	public void setValue(String value) {
		component.setValue( value);
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener listener) {
		// TODO Auto-generated method stub
		return component.addValueChangeListener(listener);
	}
	
	
}
