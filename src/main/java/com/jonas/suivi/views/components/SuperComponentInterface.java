package com.jonas.suivi.views.components;

import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.KeyNotifier;

public interface SuperComponentInterface<U extends Object, T extends Component > extends KeyNotifier, HasValue<ValueChangeEvent<U>, U> {
	
	
	public T getComponent();
		
	public U getValue();
	
//	public <W>void setValue(W value);
	
//	public Class<U> getType();
		
	public FieldDetail getFieldDetail();
	public void setFieldDetail(FieldDetail field);
	
	
	default  void onClose() {
		
	}
	
	/**
	 * Loads field using external Table
	 * @param serviceProxy
	 */
	default public void initialize(ServiceProxy serviceProxy) {
//		((KeyNotifier) getComponent()).addKeyPressListener(l ->{
//			if(l.getKey().equals(Key.ESCAPE)){
//				
//				this.getComponent().blur();
//				onClose();
//	
//			}
//		});

	}
	
}
