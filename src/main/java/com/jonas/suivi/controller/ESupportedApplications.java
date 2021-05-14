package com.jonas.suivi.controller;

import com.jonas.suivi.views.descriptors.InterventionDescriptor;
import com.jonas.suivi.views.descriptors.LanguageDescriptor;
import com.jonas.suivi.views.descriptors.PersonDescriptor;
import com.jonas.suivi.views.descriptors.ProjectDescriptor;
import com.jonas.suivi.views.descriptors.TicketDescriptor;
import com.jonas.suivi.views.descriptors.TicketNoteDescriptor;
import com.jonas.suivi.views.descriptors.TranslationDescriptor;
import com.jonas.suivi.views.descriptors.UserAccountDescriptor;
import com.jonas.suivi.views.model.Application;

public enum ESupportedApplications {

	PROJECT(ProjectDescriptor.class, true),
	INTERVENTIONS(InterventionDescriptor.class, true),
	LANGUAGE(LanguageDescriptor.class, false),
	TICKET(TicketDescriptor.class, true),
	TICKET_NOTE(TicketNoteDescriptor.class, false),
	TRANSLATION(TranslationDescriptor.class, true),
	PERSON(PersonDescriptor.class, true),
	USER_ACCOUNT(UserAccountDescriptor.class, true);
	

	Class<? extends Application> descriptor;
	boolean showApp = true;
	
	private ESupportedApplications(Class<? extends Application> app, boolean show) {
		descriptor = app;
		showApp = show;
	}

	public Class<? extends Application> getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(Class<? extends Application> descriptor) {
		this.descriptor = descriptor;
	}

	public boolean isShowApp() {
		return showApp;
	}

	public void setShowApp(boolean showApp) {
		this.showApp = showApp;
	}
	
	
}
