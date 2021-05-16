package com.jonas.suivi.views.components;

import java.beans.PropertyChangeSupport;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;

@com.vaadin.flow.component.Tag(value = "SuperComponent")
public abstract class AbstractSuperComponent<U> extends Component {

	FieldDetail field;
	boolean isReadOnly = false;
	boolean isRequired = false;

	protected PropertyChangeSupport propertyChangeSupport;

	
	public FieldDetail getFieldDetail() {
		// TODO Auto-generated method stub
		return field;
	}

	public void setFieldDetail(FieldDetail field) {
		this.field = field;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public boolean isRequiredIndicatorVisible() {
		return isRequired;
	}

	public String getFieldName() {
		return field.getName();
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
		this.propertyChangeSupport = propertyChangeSupport;
	}
	
	
	
//	public abstract void setValue(U value) ;


}
