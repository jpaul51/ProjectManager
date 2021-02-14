package com.jonas.suivi.views.components;

import java.time.LocalDateTime;

import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.shared.Registration;


public class DateTimeComponent extends AbstractSimpleSuperComponent<LocalDateTime>  {
	
	DateTimePicker component;
	LocalDateTime defaultValue;
	
	public DateTimeComponent(FieldDetail field) throws InvalidFieldDescriptorException {
		component = new DateTimePicker();
		component.setLabel(TranslationUtils.translate(field.getTranslationKey()));
		component.setLocale(TranslationUtils.locale);
		component.setAutoOpen(false);
		
		if(field.getDefaultValue() != null) {
			if(!(field.getDefaultValue() instanceof LocalDateTime)) {
				throw new InvalidFieldDescriptorException("Date field value only accepts LocalDateTime");
			}
			defaultValue = (LocalDateTime) field.getDefaultValue();
			component.setValue(defaultValue);
		}
		
	}
	
	
	@Override
	public DateTimePicker getComponent() {
		return component;
	}

	@Override
	public LocalDateTime getValue() {
		return component.getValue();
	}



	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<LocalDateTime>> listener) {
		return component.addValueChangeListener(listener);
	}


	@Override
	public void setReadOnly(boolean readOnly) {
		isReadOnly = readOnly;
		component.setReadOnly(readOnly);
	}


	@Override
	public boolean isReadOnly() {
		return isReadOnly;
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
	public void setValue(LocalDateTime value) {
		
		if(value == null && defaultValue != null) {
			value = defaultValue;
		}
		component.setValue(value);
	}




}