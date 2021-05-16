package com.jonas.suivi.backend.model;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.jonas.suivi.views.main.DetailView;
import com.jonas.suivi.views.main.EAPP_CONTEXT;
import com.jonas.suivi.views.model.Application;


public interface Displayable extends Serializable{

	
	
	public String getLabel();
	
	public Long getId();
	
	public void setId(Long id);
	
	public void setSimpleValue(String value);
	
//	public void setValue();
	
	public default  String getClazz() {
		return Displayable.class.getCanonicalName();
	}

	default void persitDisplayable(DetailView detailView) {
	
//		updateLinks(detailView);		
				
		if(detailView.appCtx.equals(EAPP_CONTEXT.ADD)) {
			detailView.serviceProxy.getInstance(this.getClass()).create(this);
		}else {
			detailView.serviceProxy.getInstance(this.getClass()).update(this);				
		}
	}

	default void updateLinks(DetailView detailView) {
		Class<Displayable> entityClazz = (Class<Displayable>) detailView.getCurrentDisplayable().getClass();
		
		detailView.gridComponents.stream().forEach(grid ->{
			Class<? extends Application> ctx = grid.getGridView().getCtx();
			Class<Displayable> entityClazzChild = grid.getGridView().getEntityClazz();
//			Class<?extends Displayable> model = entityClazzChild;
			
			Optional<Field> optFieldList = detailView.findFieldListOfModel(entityClazzChild);
		
			if(optFieldList.isPresent()) {
				Field field = optFieldList.get();
				
					PropertyDescriptor pd;
					try {
						pd = new PropertyDescriptor(field.getName(), entityClazz);
						pd.getWriteMethod().invoke(this, grid.getGridView().getDisplayables());
					
					} catch (IntrospectionException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
			}

					try {
						
						Optional<Field> optMainEntityOfChild = detailView.findFieldOfChild(entityClazz, entityClazzChild);
						if(optMainEntityOfChild.isPresent()) {
							Field field = optMainEntityOfChild.get();
							PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entityClazzChild);
	
							grid.getGridView().getDisplayables().forEach(dispChild ->{
							try {
								Displayable dd = entityClazz.getConstructor().newInstance();								
								PropertyDescriptor idPd = new PropertyDescriptor("id", entityClazz);
								idPd.getWriteMethod().invoke(dd, detailView.getCurrentDisplayable().getId());
								
								
								pd.getWriteMethod().invoke(dispChild, dd);
								
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchMethodException | SecurityException | IntrospectionException e) {
								e.printStackTrace();
							}
						});
						}

					} catch (IntrospectionException  | IllegalArgumentException e) {
						e.printStackTrace();
					}		
			
		});
		
			detailView.serviceProxy.getInstance(this.getClass()).update(this);				
		
	}
	
	
	
	
//	@Transient
//	public default List<Method> getMethodList() {
//		return Arrays.asList(getClass().getDeclaredMethods());
//	}
//	public ValueProvider<T,?> getPropertyValue( ValueProvider<? extends T,?> provider);

//	ValueProvider<? extends Serializable, ?> getPropertyValue(ValueProvider<?, ?> provider);
	
}
