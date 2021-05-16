package com.jonas.suivi.views.main;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.components.AbstractSimpleSuperComponent;
import com.jonas.suivi.views.components.AbstractSuperComponent;
import com.jonas.suivi.views.components.AbstractSuperCustomField;
import com.jonas.suivi.views.components.AbstractSuperDisplayableComponent;
import com.jonas.suivi.views.components.CheckComponent;
import com.jonas.suivi.views.components.DateTimeComponent;
import com.jonas.suivi.views.components.GridComponent;
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
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.Detail.DetailType;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class DetailView extends SingleView implements PropertyChangeListener{

	
	private Binder<Displayable> binder;

	HashMap<FieldDetail, SuperComponentInterface<?, ? extends Component>> componentsByField;
	List<SuperComponentInterface<?, ? extends Component>> formComponents;
	FormLayout formLayout = new FormLayout();

	List<PushyView> lazyComponents = new ArrayList<>();

	public ServiceProxy serviceProxy;
	
	Displayable currentDisplayable;
	Label labelId = new Label();
	
	private Button btnCancel;
	private Button btnSave;
	private Button btnDelete;
	
	public List<GridComponent> gridComponents = new ArrayList<>();
	
	List<Displayable> childrenChangedToPersist = new ArrayList<>();
	
	
	public DetailView(Class<?> context, ServiceProxy serviceProxy, ViewState viewState) {
		super(context,serviceProxy);
		this.currentViewState = viewState;
		this.appCtx = currentViewState.getEventType().equals(EViewEventType.OPEN_EDIT) ? EAPP_CONTEXT.UPDATE : EAPP_CONTEXT.ADD;
		
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
	
	
	public DetailView(Class<?> context, ServiceProxy serviceProxy) {
		this(context, serviceProxy, null);

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
			binder.readBean(object);
			
			this.gridComponents.forEach(gridComp ->{
				Class<? extends Displayable> model = gridComp.getGridView().getEntityClazz();
				
				Optional<Field> optEntity = findCurrentDisplayableOfModel(model);
		
				if(!optEntity.isEmpty()) {
					
					Field currentGridEntity = optEntity.get();
					try {
						PropertyDescriptor pd = new PropertyDescriptor(currentGridEntity.getName(), model);
						Displayable exDisp = model.getDeclaredConstructor().newInstance();
//						exDisp.setId(currentDisplayable.getId());
						Displayable currentExample = getEntityClazz().getConstructor().newInstance();
						currentExample.setId(currentDisplayable.getId());
						pd.getWriteMethod().invoke(exDisp, currentExample);
						
						ExampleMatcher matcher = Example.of(exDisp).getMatcher().withIgnoreNullValues();
						
						
						gridComp.setFilter(Example.of(exDisp, matcher));

					} catch (IntrospectionException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}

				
				gridComp.getGridView().reload();
			});
			
		}

	}


	public void setLabel() {
//		if(currentDisplayable.getId() == null && currentViewState)
		
//		labelId.setText(pageTitle.concat(" ").concat(currentDisplayable.getId().toString()));
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
				((AbstractSuperComponent) component).getPropertyChangeSupport().addPropertyChangeListener(this);
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
		case Input.ENTITY:
			component = new GridComponent<>(field, serviceProxy);
			component.getComponent().getElement().setAttribute("colspan", "2");
			((GridComponent)component).getPropertyChangeSupport().addPropertyChangeListener(this);
//			((GridView)component.getComponent()).setParentView(this);
			gridComponents.add((GridComponent) component);
			break;
		case Input.TEXT_INPUT:
		default:
			component = new TextFieldComponent(field, translatedFieldLabel);
			break;

		}
		component.setFieldDetail(field);
		component.setReadOnly(field.getReadOnly());

		if(!field.getType().equals(Input.ENTITY)) {
			binder.forField(component).bind(field.getName());
		}
		
		component.getContainer().getElement().getStyle().set("padding-top", "var(--lumo-space-m)");
		componentsByField.put(field, component);
		formComponents.add(component);
		formLayout.add(component.getContainer());
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
		formDiv.getClassNames().add("verticalNoPadding");
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

		btnCancel = new Button( new Icon(VaadinIcon.CLOSE_CIRCLE));
		btnSave = new Button( new Icon(VaadinIcon.CHECK_CIRCLE));
		btnDelete = new Button( new Icon(VaadinIcon.TRASH));
		
	
		
		btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.add(btnDelete, btnCancel, btnSave);
		buttonLayout.getClassNames().add("button-layout");
		labelLayout.getClassNames().add("label-layout");

		btnDelete.addClickListener(c -> 
			deleteDisplayable()
		);

		btnCancel.addClickShortcut(Key.ESCAPE);
		btnSave.addClickShortcut(Key.F2);
		btnDelete.addClickShortcut(Key.KEY_S, KeyModifier.ALT);

		btnCancel.addClickListener(e -> {
//			btnCancel.clickInClient();
			ViewState closeViewState = new ViewState(this, EViewEventType.CLOSE, currentDisplayable,
					viewClazz);
			propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), currentViewState,closeViewState);

		});

		btnSave.addClickListener(e -> {
			saveDisplayable();
			ViewState saveViewState = new ViewState(this, EViewEventType.SAVE, currentDisplayable,
					viewClazz);
			propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), currentViewState, saveViewState);

		});
		headerLayout.add(labelLayout, buttonLayout);
		editorDiv.add(headerLayout);
	}

	private void deleteDisplayable() {
		if (currentDisplayable != null) {
//			persistDeleteDisplayable();
			ViewState deleteViewState = new ViewState(this, EViewEventType.DELETE, currentDisplayable,
					viewClazz);
			propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), currentViewState, deleteViewState);
			
		}
	}


	public void persistDeleteDisplayable() {
		displayableService.delete(currentDisplayable);
	}

	private void saveDisplayable() {
		if (currentDisplayable == null) {
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

			application.getAction().stream().filter(a -> {
				return a.getActionType().equals(ActionType.SUBMIT) & !a.isBefore();
			}).forEach(a -> a.getServiceAction().accept(serviceProxy.getInstance(Translation.class)));
			;

			populateForm(currentDisplayable);


		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
	}



	public void persitDisplayable() {
		
		this.getChildrenChangedToPersist().forEach(d ->{
			d.persitDisplayable(this);
		});
		
		currentDisplayable.persitDisplayable(this);
		
		
		currentDisplayable.updateLinks(this);
		
		this.getChildrenChangedToPersist().forEach(d ->{
			d.updateLinks(this);
		});
		
	}


	public Optional<Field> findFieldListOfModel(Class<? extends Displayable> model) {
		Optional<Field> optField = Arrays.asList(getEntityClazz().getDeclaredFields()).stream().filter(field ->{
			  Type type =  field.getGenericType();
			  if(type instanceof ParameterizedType) {
				  ParameterizedType listArguments = (ParameterizedType) type;
				  if(listArguments != null && listArguments.getActualTypeArguments().length > 0) {
				        Class<?> entity = (Class<?>) listArguments.getActualTypeArguments()[0];
				        if(entity.equals(model)) {
				        	return true;
				        }
					  }
			  }
			 
		      return false;
		}).findFirst();
		return optField;
	}
	
	public Optional<Field> findFieldOfChild(Class<? extends Displayable> model, Class<? extends Displayable> childClazz) {
		Optional<Field> optField = Arrays.asList(childClazz.getDeclaredFields()).stream().filter(field ->{
			 
			  if(field.getType().getClass().equals(model)) {
				 
				        	return true;
					  
			  }
			 
		      return true;
		}).findFirst();
		return optField;
	}
	
	
	private Optional<Field> findCurrentDisplayableOfModel(Class<? extends Displayable> model) {
		Optional<Field> optEntity = Arrays.asList(model.getDeclaredFields()).stream().filter(field ->{
			if(field.getType().equals(getEntityClazz())) {
				return true;
			}
			 
		      return false;
		}).findFirst();
		return optEntity;
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		loadLazyComponents();
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		this.propertyChangeSupport.firePropertyChange(evt);
	}
	
	public void refreshGridComponentOfContext(ViewState viewState, boolean delete) {
		for(GridComponent gridComp : this.gridComponents) {
			
			if(gridComp.getContext().equals(viewState.getContext())) {
				
//				int indexOfSavedDisplayable = ((GridView)gridComp.getComponent()).getDisplayables().indexOf(viewState.getCurrentDisplayable()); 
				int indexOfSavedDisplayableFiltered = ((GridView)gridComp.getComponent()).getDisplayables().indexOf(viewState.getCurrentDisplayable()); 
				
				if(indexOfSavedDisplayableFiltered != -1) {
					if(delete) {
						((GridView)gridComp.getComponent()).getDisplayables().remove(viewState.getCurrentDisplayable());
					}else {
						((GridView)gridComp.getComponent()).getDisplayables().set(indexOfSavedDisplayableFiltered, viewState.getCurrentDisplayable());
					}
				}else if(!delete){
					List<Displayable> data = new ArrayList<>(((GridView)gridComp.getComponent()).getDisplayables());
					data.add(0, viewState.getCurrentDisplayable());
					((GridView)gridComp.getComponent()).getGrid().setItems(data);
				}
				
				((GridView)gridComp.getComponent()).refreshGrid();
			}
		}
	}
	/**
	 * Assuming there is only one gridView of each type !
	 * @param context
	 */
	public GridView findGridComponentOfContext(Class<? extends Application> context) {
		Optional<GridComponent> optComponent = this.gridComponents.stream().filter(g -> g.getContext().equals(context)).findFirst();
		if(optComponent.isPresent()) {
			return (GridView) optComponent.get().getComponent();
		}else {
			return null;
		}
	}


	public List<GridComponent> getGridComponents() {
		return gridComponents;
	}


	public void setGridComponents(List<GridComponent> gridComponents) {
		this.gridComponents = gridComponents;
	}


	public List<Displayable> getChildrenChangedToPersist() {
		return childrenChangedToPersist;
	}


	public void setChildrenChangedToPersist(List<Displayable> childrenChangedToPersist) {
		this.childrenChangedToPersist = childrenChangedToPersist;
	}


	public Displayable getCurrentDisplayable() {
		return currentDisplayable;
	}


	public void setCurrentDisplayable(Displayable currentDisplayable) {
		this.currentDisplayable = currentDisplayable;
	}
	
	
	
	
}
