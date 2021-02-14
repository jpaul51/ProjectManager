package com.jonas.suivi;

import com.jonas.suivi.views.main.AbstractView;

public class UserContext {

	private Class<? extends com.jonas.suivi.views.model.Application> currentClass;
	private AbstractView view;

	public Class<? extends com.jonas.suivi.views.model.Application> getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(Class<? extends com.jonas.suivi.views.model.Application> clazz) {
		this.currentClass = clazz;
	}
	
	public AbstractView getCurrentView() {
		return view;
	}
	public void setCurrentView(AbstractView view) {
		this.view = view;
	}
	
	
}
