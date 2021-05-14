package com.jonas.suivi.views.components;

import java.beans.PropertyChangeSupport;

import org.springframework.data.domain.Example;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.main.GridView;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.shared.Registration;

public class GridComponent <V extends Displayable> extends AbstractSuperDisplayableComponent<V>{

	Class<? extends Application> context;
	ServiceProxy serviceProxy;
	GridView gridView;
	protected PropertyChangeSupport propertyChangeSupport;

	
	public GridComponent(FieldDetail field, ServiceProxy serviceProxy) {
		
		Class<? extends Application> context = field.getEntityDescriptor();

		this.context = context;
		this.serviceProxy = serviceProxy;
		gridView = new GridView(context,serviceProxy);
		gridView.getStyle().set("min-height", "400px");
		gridView.getGrid().addClassName("editorNoMargin");
		gridView.getToolbar().addClassName("verticalNoPadding");
		gridView.getGrid().getStyle().set("min-height", "400px");
		
		
		propertyChangeSupport = gridView.getPropertyChangeSupport();
		
	}
	
	
	public void setFilter(Example example) {
		gridView.setExample(example);
	}
	
	public GridView getGridView() {
		return gridView;
	}




	public void setGridView(GridView gridView) {
		this.gridView = gridView;
	}




	@Override
	public Component getComponent() {
	
		return gridView;
	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<V>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		// TODO Auto-generated method stub
		
	}


	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}


	public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
		this.propertyChangeSupport = propertyChangeSupport;
	}


	public Class<? extends Application> getContext() {
		return context;
	}


	public void setContext(Class<? extends Application> context) {
		this.context = context;
	}
	
	public void reload() {
		this.gridView.reload();
	}
	
	
	

}
