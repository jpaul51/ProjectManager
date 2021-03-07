package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.jonas.suivi.backend.services.DisplayableService;

public class Action implements Serializable {

	ActionOnSubmit actionOnSubmit;
	
	ActionType actionType;
	
	Map<FieldDetail, Object> updates;
	Map<FieldDetail, Class<?>> contextUpdates;

	Map<FieldDetail, Supplier<?>> consumerUpdate;
	
	
	boolean before = true;

	private Consumer<DisplayableService> serviceAction;

	public ActionOnSubmit getActionOnSubmit() {
		return actionOnSubmit;
	}

	public void setActionOnSubmit(ActionOnSubmit actionOnSubmit) {
		this.actionOnSubmit = actionOnSubmit;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public void addFieldUpdate(FieldDetail field, Object value) {
		if(updates == null) {
			updates = new HashMap<FieldDetail, Object>();
		}
		
		updates.put(field, value);
		
	}

	
	
	
	public void addContextUpdate(FieldDetail field, Class<?> value) {
		if(contextUpdates == null) {
			contextUpdates = new HashMap<FieldDetail, Class<?>>();
		}
		
		contextUpdates .put(field, value);
		
	}
	
	
	public <T> void addConsumerUpdate(FieldDetail field, Supplier<T> value, Consumer<T> consumer) {
		
		consumerUpdate .put(field, value);
		
	}
	
	
	
	public boolean isBefore() {
		return before;
	}

	public void setBefore(boolean before) {
		this.before = before;
	}

	public Map<FieldDetail, Object> getUpdates() {
		return updates;
	}

	public void setServiceAction(Consumer<DisplayableService> consumer) {
		this.serviceAction = consumer;		
	}

	public Consumer<DisplayableService> getServiceAction() {
		return serviceAction;
	}

	
	
	

	
	
}


