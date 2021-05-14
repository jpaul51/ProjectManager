package com.jonas.suivi.views.components;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.SimpleDisplayable;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.descriptors.MainEntity;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.shared.Registration;

public class SelectComponent<V extends Displayable> extends AbstractSuperDisplayableComponent<V> {

	ComboBox<V> select = new ComboBox<>();

	Class<? extends Displayable> fieldMainEntity;

	List<? extends Displayable> valueProvider;

	FieldDetailList field;


	public SelectComponent(FieldDetailList field, Class<? extends Application> viewClazz)
			throws InvalidFieldDescriptorException {
		super();
		this.field = field;
		Class<? extends Application> descriptor = field.getEntityDescriptor();

		if (descriptor == null && field.getValueProviders() == null) {
			throw new InvalidFieldDescriptorException("Select components need an entity Descriptor ora valueProvider");
		}

		if (field.getValueProviders() != null) {

			List<Displayable> displayables = new ArrayList<Displayable>();
			
			for (Class<? extends Enum> clazz : field.getValueProviders()) {

				List<String> enumsValue = Arrays.asList(clazz.getEnumConstants()).stream().map(e -> e.name())
						.collect(Collectors.toList());
				displayables.addAll(enumsValue.stream().map(s -> {
					SimpleDisplayable t = new SimpleDisplayable(s);
					return t;
				}).collect(Collectors.toList()));

			}
			this.valueProvider = displayables;
			select.setItems((Collection<V>) displayables);
			select.setItemLabelGenerator(i -> i.getLabel());

		} else {

			select.setItemLabelGenerator(i -> i.getLabel());

		}
		fieldMainEntity = descriptor.getAnnotation(MainEntity.class).value();

		try {
			Application appDescriptor = viewClazz.getConstructor().newInstance();
			Optional<FieldDetail> matchingField = appDescriptor.getAllFields().stream().filter(f -> f.equals(field))
					.findFirst();
			if (matchingField.isPresent()) {
				select.setLabel(TranslationUtils.translate(matchingField.get().getTranslationKey()));
			}

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}


	}

	@Override
	public ComboBox<V> getComponent() {
		return select;
	}

	@Override
	public void initializeList(ServiceProxy serviceProxy) {
		super.initializeList(serviceProxy);

		if (serviceProxy != null && fieldMainEntity != null && serviceProxy.getInstance(fieldMainEntity) != null) {
			List<Displayable> savedItems = serviceProxy.getInstance(fieldMainEntity).getAll();
			// itemsserviceProxy.getInstance(fieldMainEntity).getAll());

			if (valueProvider != null) {
				if (field.isUnique()) {// we remove keys that are already persisted
					Iterator<Displayable> itVlueProvider = (Iterator<Displayable>) valueProvider.iterator();
					while (itVlueProvider.hasNext()) {

						// Removes all items that are already persisted 
						Displayable v = itVlueProvider.next();
						if (savedItems.stream().map(d -> d.getLabel()).collect(Collectors.toList())
								.contains(v.getLabel()))
							itVlueProvider.remove();

					}
				}

			} else {//no value provider -> we take all keys
				select.setItems((Collection<V>) savedItems);
			}

		}
	}

	@Override
	public void onClose() {
		super.onClose();
		select.blur();
	}

	@Override
	public V getValue() {
		return select.getValue();
	}

	@Override
	public void setValue(V value) {
		select.setValue(value);
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<V>> listener) {
		// TODO Auto-generated method stub
		return select.addValueChangeListener(listener);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
//		isReadOnly = readOnly;
		this.select.setReadOnly(readOnly);

	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
//		isRequired = true;
		select.setRequiredIndicatorVisible(requiredIndicatorVisible);
	}

	@Override
	public FieldDetail getFieldDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldDetail(FieldDetail field) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequiredIndicatorVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize() {

	}

}
