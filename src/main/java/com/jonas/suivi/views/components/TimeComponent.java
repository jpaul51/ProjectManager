package com.jonas.suivi.views.components;

import java.time.Duration;
import java.time.LocalTime;

import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.shared.Registration;

public class TimeComponent extends AbstractSimpleSuperComponent < LocalTime> {
	
	
	TimePicker component;
	
	boolean isReadOnly = false;
	boolean isRequired = false;
	
	public TimeComponent(FieldDetail field) {
		component = new TimePicker();
		component.setLabel(TranslationUtils.translate(field.getTranslationKey()));
		component.setStep(Duration.ofMinutes(10));
		component.setLocale(TranslationUtils.locale);
		component.setMaxTime(LocalTime.of(23, 59));
		setRequiredIndicatorVisible(true);
		
//		component.a
		
	}
	
	
	@Override
	public TimePicker getComponent() {
		return component;
	}

	@Override
	public LocalTime getValue() {
		return component.getValue();
	}

	@Override
	public void setValue(LocalTime value) {
		component.setValue(value);

	}


	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<LocalTime>> listener) {
		return component.addValueChangeListener(listener);
	}


	@Override
	public void setReadOnly(boolean readOnly) {
		isReadOnly = readOnly;		
	}


	@Override
	public boolean isReadOnly() {
		return isReadOnly;
	}


	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		isRequired = requiredIndicatorVisible;
	}


	@Override
	public boolean isRequiredIndicatorVisible() {
		return isRequired;
	}


	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
