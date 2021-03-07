package com.jonas.suivi.views.main;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.components.AbstractSimpleSuperComponent;
import com.jonas.suivi.views.components.AbstractSuperCustomField;
import com.jonas.suivi.views.components.AbstractSuperDisplayableComponent;
import com.jonas.suivi.views.components.CheckComponent;
import com.jonas.suivi.views.components.DateTimeComponent;
import com.jonas.suivi.views.components.LabelComponent;
import com.jonas.suivi.views.components.RichTextEditorComponent;
import com.jonas.suivi.views.components.SelectComponent;
import com.jonas.suivi.views.components.SuperComponentInterface;
import com.jonas.suivi.views.components.TextAreaComponent;
import com.jonas.suivi.views.components.TextFieldComponent;
import com.jonas.suivi.views.components.TimeComponent;
import com.jonas.suivi.views.components.container.PushyView;
import com.jonas.suivi.views.descriptors.EAppFieldsTranslation;
import com.jonas.suivi.views.descriptors.FunctionalInterfaceLocalDateTime;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.jonas.suivi.views.model.Detail.DetailType;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class DetailView extends SingleView{

	
	private Binder<Displayable> binder;

	HashMap<FieldDetail, SuperComponentInterface<?, ? extends Component>> componentsByField;
	List<SuperComponentInterface<?, ? extends Component>> formComponents;
	FormLayout formLayout = new FormLayout();

	List<PushyView> lazyComponents = new ArrayList<>();

	private ServiceProxy serviceProxy;
	
	Displayable currentDisplayable;
	Label labelId = new Label();
	
	private Button cancel;
	private Button btnSave;
	private Button btnDelete;
	
	
	public DetailView(Class<?> context, ServiceProxy serviceProxy, ViewState viewState) {
		this(context,serviceProxy);
		this.currentViewState = viewState;
	}
	
	
	public DetailView(Class<?> context, ServiceProxy serviceProxy) {
		super(context, serviceProxy);
		binder = new Binder<>(this.entityClazz);
		this.serviceProxy = serviceProxy;
		
		pageTitle = application.getAppLabelKey();
		
		if(currentViewState == null) {
			currentViewState = new ViewState(null, EViewEventType.OPEN_EDIT, viewClazz);
		}
		this.propertyChangeSupport = new PropertyChangeSupport(currentViewState);
		
		this.serviceProxy = serviceProxy;
		
		this.appCtx = currentViewState.getEventType().equals(EViewEventType.OPEN_EDIT) ? EAPP_CONTEXT.UPDATE : EAPP_CONTEXT.ADD;
		
		generateDetail();

	}
	
	private void generateDetail() {
		Detail currentDt = null;
		Optional<Detail> currentDtOpt = appCtx.equals(EAPP_CONTEXT.ADD)
				? (application.getDlManager().getDetails().stream()
						.filter(d -> DetailType.NEW.equals(d.getDetailType())).findFirst())
				: (application.getDlManager().getDetails().stream()
						.filter(d -> DetailType.UPDATE.equals(d.getDetailType())).findFirst());
		if (currentDtOpt.isEmpty()) {
			currentDt = application.getDlManager().getDefaultDetail();
		} else {
			currentDt = currentDtOpt.get();
		}

		binder = new Binder(this.entityClazz);
		componentsByField = new HashMap<>();
		formComponents = new ArrayList<SuperComponentInterface<?,? extends Component>>();
		formLayout = new FormLayout();
		
		createEditorLayout();

		for (Bloc b : currentDt.getBlocs()) {

			for (Line l : b.getLines()) {

				for (FieldDetail f : l.getFields()) {

					createBinder(f);

				}

			}

		}
			reloadEntityComponents();

	}

	public void loadLazyComponents() {
		for (PushyView view : lazyComponents) {
			view.perform();
		}
	}

	

	

	private void reloadEntityComponents() {
		findEntityFieldsComponents().stream().forEach(s -> 
			s.initializeList(serviceProxy)
		);
	}
	
	private List<SuperComponentInterface<?, ? extends Component>> findEntityFieldsComponents() {
		return componentsByField.entrySet().stream().filter(e -> e.getKey().getEntityDescriptor() != null || e.getKey() instanceof FieldDetailList)
				.map(m -> m.getValue()).collect(Collectors.toList());
	}
	
	@Override
	public void reload() {
		populateForm(currentViewState.getCurrentDisplayable());
		
	}

	public void populateForm(Displayable object) {
		
		if (object == null) {
			
			labelId.setText(TranslationUtils.translate(EAppFieldsTranslation.APP_FIELDS_ADD.name()).concat(" ").concat(TranslationUtils.translate(pageTitle)));
			currentDisplayable = null;
			for (SuperComponentInterface<?, ? extends Component> disp : formComponents) {
				disp.initialize();
			}
		} else {

			this.currentDisplayable = object;
			labelId.setText(pageTitle.concat(" ").concat(currentDisplayable.getId().toString()));
			binder.readBean(object);
		}

	}



	private void createBinder(FieldDetail field) {

		
		
		
		String translatedFieldLabel = TranslationUtils.translate(field.getTranslationKey());

		String fieldType = field.getType();
		if (fieldType == null) {
			fieldType = Input.TEXT_INPUT;
		}

		SuperComponentInterface<?, ?> component;

		switch (fieldType) {

		case Input.TEXT_RICH:
			component = new RichTextEditorComponent(field);
			component.getComponent().getElement().setAttribute("colspan", "2");
			lazyComponents.add((PushyView) component.getComponent());
			break;

		case Input.TEXT_AREA:
			component = new TextAreaComponent(field, translatedFieldLabel);
			break;

		case Input.CHECK_INPUT:
			component = new CheckComponent(field, translatedFieldLabel);
			break;

		case Input.SELECT:
			try {
				component = new SelectComponent<>((FieldDetailList)field, viewClazz);
			} catch (InvalidFieldDescriptorException e) {
				e.printStackTrace();
				component = new LabelComponent(field, e.getLocalizedMessage());
			}
			break;

		case Input.DATE_TIME:
			try {
				component = new DateTimeComponent(field);
			} catch (InvalidFieldDescriptorException e) {
				e.printStackTrace();
				component = new LabelComponent(field, e.getLocalizedMessage());
			}
			break;
		case Input.DATE_TIME_ONLY:
			component = new TimeComponent(field);
			break;
		case Input.TEXT_INPUT:
		default:
			component = new TextFieldComponent(field, translatedFieldLabel);
			break;

		}
		component.setFieldDetail(field);
		component.setReadOnly(field.getReadOnly());

		
		binder.forField(component).bind(field.getName());
		componentsByField.put(field, component);
		formComponents.add(component);
		formLayout.add(component.getComponent());
	}

	private void createEditorLayout( ) {
		
		Div panelDiv = new Div();
		createButtonLayout(panelDiv);
		panelDiv.setId("editor-layout");
		panelDiv.setWidthFull();

		Div formDiv = new Div();

		formLayout.getStyle().set("overflow", "hidden");

		formDiv.add(formLayout);
		panelDiv.add(formDiv);
		formDiv.getStyle().set("overflow-y", "auto");

		formDiv.getClassNames().add("scrollable-div");
		panelDiv.getStyle().set("overflow", "hidden");
		formDiv.getStyle().set("padding", "var(--lumo-space-m)");
		panelDiv.getStyle().set("padding", "0px");
		this.add(panelDiv);

	}

	private void createButtonLayout(Div editorDiv) {
		HorizontalLayout headerLayout = new HorizontalLayout();

		HorizontalLayout labelLayout = new HorizontalLayout();
		labelLayout.add(labelId);

		labelLayout.getClassNames().add("editorHeader");

		headerLayout.getClassNames().add(CLASS_HEADER_LAYOUT);
		headerLayout.setId(CLASS_HEADER_LAYOUT);
		headerLayout.setWidthFull();
		headerLayout.setSpacing(true);

		cancel = new Button("Cancel");
		btnSave = new Button("Save");
		btnDelete = new Button("Delete");
		
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.add(btnDelete, cancel, btnSave);
		buttonLayout.getClassNames().add("button-layout");
		labelLayout.getClassNames().add("label-layout");

		btnDelete.addClickListener(c -> 
			deleteDisplayable()
		);

		cancel.addClickShortcut(Key.ESCAPE);
		btnSave.addClickShortcut(Key.F2);
		btnDelete.addClickShortcut(Key.KEY_S, KeyModifier.ALT);

		cancel.addClickListener(e -> {
			cancel.clickInClient();
			ViewState closeViewState = new ViewState(this, EViewEventType.CLOSE, currentDisplayable,
					viewClazz);
			propertyChangeSupport.firePropertyChange("viewState", currentViewState,closeViewState);

		});

		btnSave.addClickListener(e -> {
			saveDisplayable();
			ViewState saveViewState = new ViewState(this, EViewEventType.SAVE, currentDisplayable,
					viewClazz);
			propertyChangeSupport.firePropertyChange("viewState", currentViewState, saveViewState);

		});
		headerLayout.add(labelLayout, buttonLayout);
		editorDiv.add(headerLayout);
	}

	private void deleteDisplayable() {
		if (currentDisplayable != null) {
			displayableService.delete(currentDisplayable);
			ViewState deleteViewState = new ViewState(this, EViewEventType.DELETE, 
					viewClazz);
			propertyChangeSupport.firePropertyChange("viewState", currentViewState, deleteViewState);
			
		}
	}

	private void saveDisplayable() {
		boolean isNew = false;
		if (currentDisplayable == null) {
			isNew = true;
			try {
				currentDisplayable = entityClazz.getConstructor().newInstance();

				Iterator<Entry<FieldDetail, PropertyDescriptor>> it = propDescriptorByField.entrySet().iterator();

				while (it.hasNext()) {
					Entry<FieldDetail, PropertyDescriptor> entry = it.next();
					FieldDetail fd = entry.getKey();
					PropertyDescriptor pd = entry.getValue();
					if (fd != null && componentsByField.get(fd) != null)
						pd.getWriteMethod().invoke(currentDisplayable, componentsByField.get(fd).getValue());
				}

			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
		try {

			application.getAction().stream().filter(a -> 
				 a.getActionType().equals(ActionType.SUBMIT) & a.isBefore()
			).forEach(a -> {
				if (a.getUpdates() != null) {

					Iterator<Entry<FieldDetail, Object>> updates = a.getUpdates().entrySet().iterator();

					while (updates.hasNext()) {
						Entry<FieldDetail, Object> entry = updates.next();
						FieldDetail field = entry.getKey();
						Object updatedValue = field.getDefaultValue();

						if(updatedValue instanceof FunctionalInterfaceLocalDateTime) {
							updatedValue = ((FunctionalInterfaceLocalDateTime) updatedValue).now();
						}
						
						SuperComponentInterface<?, ?> component = componentsByField.get(field);
						if (component instanceof AbstractSimpleSuperComponent) {
							((AbstractSimpleSuperComponent<Object>) componentsByField.get(field))
									.setValue((updatedValue));
						} else if (component instanceof AbstractSuperCustomField) {
							((AbstractSuperCustomField) componentsByField.get(field)).setValue((String) (updatedValue));
						} else if (component instanceof AbstractSuperDisplayableComponent) {
							((AbstractSuperDisplayableComponent) componentsByField.get(field)).setValue((updatedValue));

						}
					}
				}

			});

			binder.writeBean(currentDisplayable);

			if(this.appCtx.equals(EAPP_CONTEXT.ADD)) {
				displayableService.create(currentDisplayable);
			}else {
				displayableService.update(currentDisplayable);				
			}

			application.getAction().stream().filter(a -> {
				return a.getActionType().equals(ActionType.SUBMIT) & !a.isBefore();
			}).forEach(a -> a.getServiceAction().accept(serviceProxy.getInstance(Translation.class)));
			;

			populateForm(currentDisplayable);
			if (!isNew) {
//				grid.getDataProvider().refreshItem(currentDisplayable);
			} else {
//				displayables.add(0, currentDisplayable);
//				grid.setItems(displayables);
			}

		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		loadLazyComponents();
	}
	
	
	
}
