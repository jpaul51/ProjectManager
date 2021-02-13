package com.jonas.suivi.backend.model;

import javax.persistence.AttributeConverter;

import com.jonas.suivi.backend.model.impl.SimpleDisplayable;

public class SimpleDisplayableConverter implements AttributeConverter<SimpleDisplayable, String> {

	@Override
	public String convertToDatabaseColumn(SimpleDisplayable attribute) {
		// TODO Auto-generated method stub
		return attribute.getValue();
	}

	@Override
	public SimpleDisplayable convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return new SimpleDisplayable(dbData);
	}

}
