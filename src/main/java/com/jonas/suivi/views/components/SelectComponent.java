package com.jonas.suivi.views.components;

import java.beans.PropertyChangeSupport;
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
import com.jonas.suivi.views.main.DetailView;
import com.jonas.suivi.views.main.ESplitViewEvents;
import com.jonas.suivi.views.main.EViewEventType;
import com.jonas.suivi.views.main.ViewState;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

public class SelectComponent<V extends Displayable> extends AbstractSuperDisplayableComponent<V> {

	ComboBox<V> select = new ComboBox<>();

	HorizontalLayout layout = new HorizontalLayout();

	Class<? extends Displayable> fieldMainEntity;

	List<? extends Displayable> valueProvider;

	FieldDetailList field;

	String customValue = null;

	private List<Displayable> savedItems;

	public SelectComponent(FieldDetailList field, Class<? extends Application> viewClazz)
			throws InvalidFieldDescriptorException {
		super();
		this.field = field;
		Class<? extends Application> descriptor = field.getEntityDescriptor();

		if (descriptor == null && field.getValueProviders() == null) {
			throw new InvalidFieldDescriptorException("Select components need an entity Descriptor ora valueProvider");
		}

		ViewState currentViewState = new ViewState(null, EViewEventType.LOAD, viewClazz);

		this.propertyChangeSupport = new PropertyChangeSupport(currentViewState);

		Div container = new Div();
		HorizontalLayout hl = new HorizontalLayout();
		hl.add(select);
		container.add(hl);

		Button btnEdit = new Button("EDIT");
		btnEdit.setVisible(false);
		Button btnAdd = new Button("+");

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
			select.setAllowCustomValue(true);
			select.addCustomValueSetListener(ce -> {
				customValue = ce.getDetail();
				btnEdit.setVisible(false);

			});
			select.addValueChangeListener(v -> {
				btnEdit.setVisible(true);
			});

			select.setItemLabelGenerator(i -> i.getLabel());

			HorizontalLayout btnLayout = new HorizontalLayout();

			btnAdd.addClickListener(c -> {

				fireAddEvent(descriptor);

			});

			btnEdit.addClickListener(c -> {

				fireEditEvent(descriptor);

			});

			btnLayout.add(btnEdit, btnAdd);
			btnLayout.getStyle().set("padding-top", "32px");
			hl.add(btnLayout);
			layout.add(container);
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

	private void fireAddEvent(Class<? extends Application> descriptor) {
		Class entityClazz = descriptor.getAnnotation(MainEntity.class).value();

		try {
			Displayable disp = (Displayable) entityClazz.getConstructor().newInstance();
			disp.setSimpleValue(customValue);

			ViewState openEditViewState = new ViewState(this, EViewEventType.OPEN_NEW, disp, descriptor);

			ViewState odlViewState = new ViewState(this, EViewEventType.LOAD, new SimpleDisplayable(""), descriptor);

			propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), odlViewState,
					openEditViewState);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fireEditEvent(Class<? extends Application> descriptor) {
		Class entityClazz = descriptor.getAnnotation(MainEntity.class).value();

		Displayable disp = select.getValue();

		ViewState openEditViewState = new ViewState(this, EViewEventType.OPEN_EDIT, disp, descriptor);

		ViewState odlViewState = new ViewState(this, EViewEventType.LOAD, disp, descriptor);

		propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), odlViewState,
				openEditViewState);

	}

	@Override
	public ComboBox<V> getComponent() {
		return select;
	}

	@Override
	public Component getContainer() {
		return layout;
	}

	@Override
	public void initializeList(ServiceProxy serviceProxy) {
		super.initializeList(serviceProxy);

		if (serviceProxy != null && fieldMainEntity != null && serviceProxy.getInstance(fieldMainEntity) != null) {
			savedItems = serviceProxy.getInstance(fieldMainEntity).getAll();

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

			} else {// no value provider -> we take all keys
				select.setItems((Collection<V>) savedItems);
			}

		}
	}

	@Override
	public void addValue(V val) {
		if (savedItems.contains(val)) {
			this.editValue(val);
		} else {
			savedItems.add(val);
			getComponent().setItems((Collection<V>) savedItems);
			getComponent().setValue(val);
		}

	}

	@Override
	public void editValue(V val) {
		Optional<Displayable> optDisp = savedItems.stream().filter(d -> d.equals(val)).findFirst();
		if (optDisp.isPresent()) {
			Displayable disp = optDisp.get();

			int dispIndex = savedItems.indexOf(disp);
			savedItems.set(dispIndex, val);

			getComponent().setItems((Collection<V>) savedItems);
			getComponent().setValue(val);

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
