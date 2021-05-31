package com.jonas.suivi.views.components;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.shared.Registration;

public class DateComponent extends AbstractSimpleSuperComponent < LocalDate> {
	
	
	DatePicker component;
	
	HorizontalLayout hl = new HorizontalLayout();
	
	boolean isReadOnly = false;
	boolean isRequired = false;
	
	public DateComponent(FieldDetail field) {
		component = new DatePicker();
		component.setLabel(TranslationUtils.translate(field.getTranslationKey()));
		component.setLocale(TranslationUtils.locale);
		setRequiredIndicatorVisible(true);
		hl.add(component);
		
		
	}
	
	
	@Override
	public DatePicker getComponent() {
		return component;
	}

	@Override
	public Component getContainer() {
		// TODO Auto-generated method stub
		return hl;
	}
	
	@Override
	public LocalDate getValue() {
		return component.getValue();
	}

	@Override
	public void setValue(LocalDate value) {
		component.setValue(value);

	}


	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<LocalDate>> listener) {
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

