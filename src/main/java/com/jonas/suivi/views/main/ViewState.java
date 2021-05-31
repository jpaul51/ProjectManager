package com.jonas.suivi.views.main;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.views.components.AbstractSuperComponent;
import com.jonas.suivi.views.model.Application;
import com.vaadin.flow.component.Component;

public class ViewState {

	
	Component sender;
	EViewEventType eventType;
	Displayable currentDisplayable;
	Class<? extends Application> context;
	
	

	public ViewState(SingleView sender, EViewEventType eventType, Class<? extends Application> context) {
		super();
		this.sender = sender;
		this.eventType = eventType;
		this.context = context;
	}
	
	public ViewState(SingleView sender, EViewEventType eventType, Displayable currentDisplayable,
			Class<? extends Application> context) {
		super();
		this.sender = sender;
		this.eventType = eventType;
		this.currentDisplayable = currentDisplayable;
		this.context = context;
	}
	
	public ViewState(Component sender, EViewEventType eventType, Displayable currentDisplayable,
			Class<? extends Application> context) {
		super();
		this.sender = sender;
		this.eventType = eventType;
		this.currentDisplayable = currentDisplayable;
		this.context = context;
	}

	public Component getSender() {
		return sender;
	}

	public boolean isSenderSingleView() {
		return sender instanceof SingleView;
	}
	
	public boolean isSenderAComponent() {
		return sender instanceof AbstractSuperComponent;
	}
	
	public void setSender(SingleView sender) {
		this.sender = sender;
	}

	public EViewEventType getEventType() {
		return eventType;
	}

	public void setEventType(EViewEventType eventType) {
		this.eventType = eventType;
	}

	public <T extends Displayable> T getCurrentDisplayable() {
		return (T) currentDisplayable;
	}

	public void setCurrentDisplayable(Displayable currentDisplayable) {
		this.currentDisplayable = currentDisplayable;
	}

	public Class<? extends Application> getContext() {
		return context;
	}

	public void setContext(Class<? extends Application> context) {
		this.context = context;
	}
	
	
	
}
