package com.jonas.suivi.views.components;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.shared.Registration;

public class CheckComponent extends AbstractSimpleSuperComponent<Boolean>{

	
	Checkbox checkBox = new Checkbox();
	
	
	public CheckComponent(FieldDetail field, String label) {
		checkBox.setLabel(label);
	}
	
	@Override
	public Component getContainer() {
		return checkBox;
	}
	
	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return checkBox;
	}

	@Override
	public Boolean getValue() {
		// TODO Auto-generated method stub
		return checkBox.getValue();
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Boolean>> listener) {
		
		
		return checkBox.addValueChangeListener(listener);
		
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		checkBox.setReadOnly(readOnly);
	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		checkBox.setRequiredIndicatorVisible(requiredIndicatorVisible);
		
	}



	@Override
	public void setValue(Boolean value) {
		if(value == null) {
		value = false;
	}
	checkBox.setValue(value);		
	}


	@Override
	public void initialize() {
		setValue(false);
	}


	

}
