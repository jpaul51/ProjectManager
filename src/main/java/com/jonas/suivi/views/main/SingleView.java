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
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import net.bytebuddy.asm.Advice.This;

public abstract class SingleView extends VerticalLayout implements DoubleClickListener{

	Application application;
	String pageTitle;


	protected Class<? extends Application> viewClazz;
	public Class<Displayable> entityClazz;
	protected Div toolbar;
	
	protected 	HashMap<FieldDetail, PropertyDescriptor> propDescriptorByField = new HashMap<>();

	protected PropertyChangeSupport propertyChangeSupport;
	
	ViewState currentViewState;
	
	
	public EAPP_CONTEXT appCtx;
	
	protected final String CLASS_HEADER_LAYOUT = "header-layout";

	public DisplayableService displayableService;
	
	public ServiceProxy serviceProxy;
	
	
	public SingleView(Class<?> context, ServiceProxy serviceProxy) {
		this.serviceProxy = serviceProxy;
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
	
	
	
	public Class<Displayable> getEntityClazz() {
		return entityClazz;
	}



	public Div getToolbar() {
		return toolbar;
	}



	public void setToolbar(Div toolbar) {
		this.toolbar = toolbar;
	}



	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
		this.propertyChangeSupport = propertyChangeSupport;
	}

	public ViewState getCurrentViewState() {
		return currentViewState;
	}



	public void setCurrentViewState(ViewState currentViewState) {
		this.currentViewState = currentViewState;
	}


	public abstract void reload();



	public Class<? extends Application> getCtx() {
		return viewClazz;
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {
		this.propertyChangeSupport.firePropertyChange(ESplitViewEvents.DBL_CLICK.getValue(), null, this);
		
	}
	
	public String getPageTitle() {
		return pageTitle;
	}



	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
}
