package com.jonas.suivi.views.components;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;

@com.vaadin.flow.component.Tag(value = "SuperComponent")
public abstract class AbstractSuperComponent<U> extends Component {

	FieldDetail field;
	boolean isReadOnly = false;
	boolean isRequired = false;

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
	
//	public abstract void setValue(U value) ;


}
