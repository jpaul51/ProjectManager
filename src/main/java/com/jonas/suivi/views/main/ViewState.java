package com.jonas.suivi.views.main;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.views.model.Application;

public class ViewState {

	
	SingleView sender;
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

	public SingleView getSender() {
		return sender;
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

	public Displayable getCurrentDisplayable() {
		return currentDisplayable;
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
