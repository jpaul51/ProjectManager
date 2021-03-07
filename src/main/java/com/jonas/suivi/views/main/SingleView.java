package com.jonas.suivi.views.main;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.services.DisplayableService;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.descriptors.MainEntity;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class SingleView extends VerticalLayout{

	Application application;
	String pageTitle;
	protected Class<? extends Application> viewClazz;
	protected Class<Displayable> entityClazz;
	protected Div toolbar;
	
	protected 	HashMap<FieldDetail, PropertyDescriptor> propDescriptorByField = new HashMap<>();

	protected PropertyChangeSupport propertyChangeSupport;
	
	ViewState currentViewState;
	
	
	protected EAPP_CONTEXT appCtx;
	
	protected final String CLASS_HEADER_LAYOUT = "header-layout";

	protected DisplayableService displayableService;
	
	protected SingleView parentView = null;
	
	public SingleView(Class<?> context, ServiceProxy serviceProxy, SingleView parent) {
		this(context, serviceProxy);
		this.parentView = parent;
	}
	
	public SingleView(Class<?> context, ServiceProxy serviceProxy) {
		if (context != null) {
			viewClazz = (Class<? extends Application>) context;
			try {
				application = (Application) viewClazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			
			
			if (viewClazz == null) {
				UI.getCurrent().navigate("");
			}
			if (viewClazz.getAnnotation(MainEntity.class) == null) {
				UI.getCurrent().navigate("");

			}

			entityClazz = (Class<Displayable>) viewClazz.getAnnotation(MainEntity.class).value();
			
			displayableService = serviceProxy.getInstance(entityClazz);
			
			

		}
		
		
	}
	
	
	
	public ViewState getCurrentViewState() {
		return currentViewState;
	}



	public void setCurrentViewState(ViewState currentViewState) {
		this.currentViewState = currentViewState;
	}

	

	public SingleView getParentView() {
		return parentView;
	}

	public void setParentView(SingleView parentView) {
		this.parentView = parentView;
	}

	public abstract void reload();
	
	
}
