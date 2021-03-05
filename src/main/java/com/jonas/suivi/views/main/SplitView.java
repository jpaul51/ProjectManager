package com.jonas.suivi.views.main;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.jonas.suivi.UserContextFactory;
import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.Name;
import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.backend.services.DisplayableService;
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
import com.jonas.suivi.views.descriptors.InvalidActionDescriptorException;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.descriptors.MainEntity;
import com.jonas.suivi.views.model.Action;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.Detail.DetailType;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.jonas.suivi.views.model.ResultView;
import com.jonas.suivi.views.model.TableLayoutManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

@Route(value = "app", layout = MainView.class)
public class SplitView extends AbstractView implements HasUrlParameter<String> {

	private static final String CLASS_HEADER_LAYOUT = "header-layout";

	@Autowired
	UserContextFactory userContextFactory;

	@Autowired
	ServiceProxy serviceProxy;


	DisplayableService displayableService;

	Label labelId = new Label();

	/**
	 * 
	 */
	private static final long serialVersionUID = 222222L;

	String pageTitle = "vide";

	Class<? extends Application> viewClazz;
	Class<? extends Displayable> modelClazz;
	Grid<Displayable> grid;

	private Binder<Displayable> binder;

	private Button cancel;
	private Button btnSave;
	private Button btnDelete;

	Application application;

	EAPP_CONTEXT appCtx = EAPP_CONTEXT.ADD;

	FormLayout formLayout = new FormLayout();
	List<SuperComponentInterface<?, ? extends Component>> formComponents;

	HashMap<FieldDetail, SuperComponentInterface<?, ? extends Component>> componentsByField;

	SplitLayout splitLayout;
	Div toolbar;

	Displayable currentDisplayable;
	List<Displayable> displayables = new ArrayList<>();
	List<Displayable> filteredDisplayables = new ArrayList<>();


	List<PushyView> lazyComponents = new ArrayList<>();

	HashMap<FieldDetail, PropertyDescriptor> propDescriptorByField = new HashMap<>();

	public SplitView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {
		super();

		generateView();

	}

	private void generateView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {

		cancel = new Button("Cancel");
		btnSave = new Button("Save");
		btnDelete = new Button("Delete");
		formComponents = new ArrayList<>();
		componentsByField = new HashMap<>();

		Class<?> cl = UserContextFactory.getCurrentUserContext().getCurrentClass();
		if (cl != null) {
			pageTitle = cl.getName();
			viewClazz = (Class<? extends Application>) cl;
			try {
				application = viewClazz.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}

		}
		if (viewClazz == null) {
			UI.getCurrent().navigate("");
		}
		if (viewClazz.getAnnotation(MainEntity.class) == null) {
			UI.getCurrent().navigate("");

		}
		modelClazz = viewClazz.getAnnotation(MainEntity.class).value();

		Class<Displayable> clazz = (Class<Displayable>) modelClazz;

		UI.getCurrent().getPage().getHistory().pushState(null, "app");

		binder = new Binder<>(clazz);

		grid = new Grid<>();
		grid.addClassName("my-grid");
		grid.getStyle().set("margin", "var(--lumo-space-m)");
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.setHeight("300px");

		grid.setSizeFull();

		VerticalLayout vl = initToolbar();

		splitLayout = new SplitLayout();

		vl.add(grid);
		vl.getStyle().set("padding", "0px");
		vl.getStyle().set("overflow-x", "hidden");
		splitLayout.addToPrimary(vl);
		splitLayout.setSizeFull();

		TableLayoutManager tlManager = application.getTlManager();

		if (tlManager == null) {

			tlManager = new TableLayoutManager();
		}
		ResultView resultView = tlManager.getDefaultResultView();

		if (resultView.getColumns().isEmpty()) {
			resultView.setColumns(application.getAllFields());
		}


		for (FieldDetail column : resultView.getColumns()) {
			if (column.getName() == null) {
				throw new InvalidFieldDescriptorException("Field name is mandatory and must match a field");
			}
			if (column.getTranslationKey() == null) {
				throw new InvalidFieldDescriptorException("Field translation key is mandatory");
			}

			Column c;
			if (column.getType().equals(Input.TEXT_RICH)) {
				c = grid.addColumn(new ComponentRenderer<>(disp -> {

					StringBuilder stringcontent = new StringBuilder("<div class='htmlContainer'>");

					Optional<Object> valueProvided = createGridColumns(column, disp);

					stringcontent.append(valueProvided.isPresent() ? valueProvided.get() : "")

							.append("</div>");

					return  new Html(stringcontent.toString());

				}));
			} else if (column.getType().equals(Input.DATE_TIME)) {
				c = grid.addColumn(new ComponentRenderer<>(disp -> {
					Div d = new Div();
//	        		LocalDateTime
					DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
					formatter = formatter.localizedBy(TranslationUtils.locale);

					Optional<Object> providedValue = createGridColumns(column, disp);

					StringBuilder value;

					if (providedValue.isPresent()) {
						value = new StringBuilder().append(((LocalDateTime) providedValue.get()).format(formatter));
					} else {
						value = new StringBuilder();
					}
					Label l = new Label(value.toString());
					l.getStyle().clear().set("pointer-events", "none");
					
					d.add(l);
					return d;
				}));
			} else {
				c = grid.addColumn(createGridColumns(column));
			}

			HtmlComponent htmlHeader = new HtmlComponent(Tag.H3);
			htmlHeader.getElement().setText(TranslationUtils.translate(column.getTranslationKey()));
			htmlHeader.addClassName("gridHeader");
			c.setHeader(htmlHeader);

		}

		generateDetail();

		for (Action ac : application.getAction()) {
			if (ac.getActionType() == null) {
				throw new InvalidActionDescriptorException("Actiontype is mandatory");
			}
			// TODO: gérer les actions onchange et similaires
		}

		this.pageTitle = TranslationUtils.translate(this.application.getAppLabelKey());

		grid.addSelectionListener(e -> {
			Optional<Displayable> oDisplayable = e.getFirstSelectedItem();
			if (oDisplayable.isPresent()) {

				final EAPP_CONTEXT currentCtx = appCtx;

				appCtx = EAPP_CONTEXT.UPDATE;

				
				if (!currentCtx.equals(EAPP_CONTEXT.UPDATE)) {
					generateDetail();
				}

				
				e.getSource().select(oDisplayable.get());
				populateForm(oDisplayable.get());
				splitLayout.getSecondaryComponent().setVisible(true);
				splitLayout.setSplitterPosition(70);

				loadLazyComponents();

			}
		});

		GridSingleSelectionModel<Displayable> m = (GridSingleSelectionModel<Displayable>) grid.getSelectionModel();
		m.setDeselectAllowed(false);

		this.add(splitLayout);
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

		binder = new Binder(this.modelClazz);
		componentsByField = new HashMap<>();
		formComponents = new ArrayList<SuperComponentInterface<?,? extends Component>>();
		formLayout = new FormLayout();
		
		createEditorLayout(splitLayout);

		for (Bloc b : currentDt.getBlocs()) {

			for (Line l : b.getLines()) {

				for (FieldDetail f : l.getFields()) {

					createBinder(f);

				}

			}

		}
			reloadEntityComponents();

	}

	private void loadLazyComponents() {
		for (PushyView view : lazyComponents) {
			view.perform();
		}
	}

	private Optional<Object> createGridColumns(FieldDetail column, Displayable disp) {
		String fieldName = column.getName();

		Optional ret;

		PropertyDescriptor pd = null;
		try {
			for (Field field : modelClazz.getDeclaredFields()) {
				if (field.getAnnotation(Name.class) != null) {
					String currfieldName = field.getAnnotation(Name.class).value();
					if (currfieldName.equals(fieldName)) {
						fieldName = currfieldName;
						pd = new PropertyDescriptor(currfieldName, modelClazz);
					}
				}
			}
			if (pd == null) {
				pd = new PropertyDescriptor(fieldName, modelClazz);
			}
			propDescriptorByField.put(column, pd);

			Object res = pd.getReadMethod().invoke(disp);

			ret = Optional.ofNullable(res);

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private VerticalLayout initToolbar() {
		toolbar = new Div();
		toolbar.setWidthFull();
		toolbar.getElement().getClassList().add(CLASS_HEADER_LAYOUT);

		HorizontalLayout hl = new HorizontalLayout();

		Button btnAdd = new Button(new Icon(VaadinIcon.PLUS));
		btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		hl.add(btnAdd);
		toolbar.add(hl);

		btnAdd.addClickListener(e -> {

			final EAPP_CONTEXT currentCtx = appCtx;

			
			appCtx = EAPP_CONTEXT.ADD;
			if (!currentCtx.equals(EAPP_CONTEXT.ADD)) {
				generateDetail();
			}

			populateForm(null);
			grid.deselectAll();
			grid.asSingleSelect().clear();
			splitLayout.getSecondaryComponent().setVisible(true);
			loadLazyComponents();
		});
		
		TextField sbox = new TextField();
		sbox.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
		sbox.addKeyPressListener(Key.ENTER, sf -> {
			String searchedValue = sbox.getValue();
			filteredDisplayables.clear();
			filteredDisplayables.addAll(displayables.stream().filter(d ->{
				return application.getTlManager().getDefaultResultView().getQuickSearchList().stream().anyMatch(f ->{
					try {
						Object displayableValue = propDescriptorByField.get(f).getReadMethod().invoke(d);
						
						return (((String)displayableValue).toLowerCase().contains(searchedValue.toLowerCase().strip())); 
							
						
						
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						e1.printStackTrace();
						return false;
					}
				});
				
				
				
			
			}).collect(Collectors.toList()));
			grid.setItems(filteredDisplayables);
		});
		Div searchDiv = new Div();
		searchDiv.add(sbox);
		sbox.focus();
		hl.add(searchDiv);
		
		toolbar.getStyle().set("padding", "var(--lumo-space-s) var(--lumo-space-l)");
		toolbar.getStyle().set("border-right", "10px");
		toolbar.getStyle().set("border-color", "#66a8ff");
		VerticalLayout vl = new VerticalLayout();
		vl.add(toolbar);
		return vl;
	}

	private void reloadEntityComponents() {
		findEntityFieldsComponents().stream().forEach(s -> 
			s.initializeList(serviceProxy)
		);
	}

	private void populateForm(Displayable object) {
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

	private ValueProvider<Displayable, ?> createGridColumns(FieldDetail column) {
		return disp -> {

			try {
				String fieldName = column.getName();

				PropertyDescriptor pd = null;

				for (Field field : modelClazz.getDeclaredFields()) {
					if (field.getAnnotation(Name.class) != null) {
						String currfieldName = field.getAnnotation(Name.class).value();
						if (currfieldName.equals(fieldName)) {
							fieldName = currfieldName;
							pd = new PropertyDescriptor(currfieldName, modelClazz);
						}
					}
				}

				if (pd == null) {
					pd = new PropertyDescriptor(fieldName, modelClazz);
				}
				propDescriptorByField.put(column, pd);
				return pd.getReadMethod().invoke(disp);

			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | InvocationTargetException
					| IntrospectionException e) {
				e.printStackTrace();
				return null;
			}
		};
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

	private void createEditorLayout(SplitLayout splitLayout) {
		
		Div panelDiv = new Div();
		createButtonLayout(panelDiv);
		panelDiv.setId("editor-layout");
		panelDiv.setWidthFull();

		Div formDiv = new Div();

		formLayout.getStyle().set("overflow", "hidden");

		panelDiv.setVisible(false);
		formDiv.add(formLayout);
		panelDiv.add(formDiv);
		formDiv.getStyle().set("overflow-y", "auto");

		formDiv.getClassNames().add("scrollable-div");
		panelDiv.getStyle().set("overflow", "hidden");
		formDiv.getStyle().set("padding", "var(--lumo-space-m)");
		panelDiv.getStyle().set("padding", "0px");
		splitLayout.addToSecondary(panelDiv);

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
			grid.asSingleSelect().clear();
			cancel.clickInClient();
			splitLayout.getSecondaryComponent().setVisible(false);
			grid.focus();

		});

		btnSave.addClickListener(e -> {
			saveDisplayable();
			refreshGrid();

		});
		headerLayout.add(labelLayout, buttonLayout);
		editorDiv.add(headerLayout);
	}

	private void deleteDisplayable() {
		if (currentDisplayable != null) {
			displayableService.delete(currentDisplayable);

			displayables.remove(currentDisplayable);
			grid.setItems(displayables);
			cancel.clickInClient();
			cancel.click();
		}
	}

	private void saveDisplayable() {
		boolean isNew = false;
		if (currentDisplayable == null) {
			isNew = true;
			try {
				currentDisplayable = modelClazz.getConstructor().newInstance();

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
				grid.getDataProvider().refreshItem(currentDisplayable);
			} else {
				displayables.add(0, currentDisplayable);
				grid.setItems(displayables);
			}

		} catch (ValidationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		// TODO Auto-generated method stub

	}

	public void reloadView() {
		formLayout.removeAll();
		this.removeAll();
		try {
			generateView();
		} catch (InvalidFieldDescriptorException | InvalidActionDescriptorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Location location = event.getLocation();
//		QueryParameters queryParameters = location.getQueryParameters();

		refreshGrid();

		findEntityFieldsComponents().stream().forEach(s -> {
			s.initializeList(serviceProxy);
		});

		if (application.getAppLabelKey() == null) {
			pageTitle = "UNDEFINED";
		} else {
			pageTitle = application.getAppLabelKey();
		}
//			pagetit
	}

	private void refreshGrid() {
		displayableService = serviceProxy.getInstance(modelClazz);
		displayables = displayableService
				.getWithSorting(application.getTlManager().getDefaultResultView().getSortField());

		this.grid.setItems(displayables);
	}

	private List<SuperComponentInterface<?, ? extends Component>> findEntityFieldsComponents() {
		return componentsByField.entrySet().stream().filter(e -> e.getKey().getEntityDescriptor() != null || e.getKey() instanceof FieldDetailList)
				.map(m -> m.getValue()).collect(Collectors.toList());
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		if (parameter != null) {

		}
	}

	@Override
	public String getPageTitle() {
		reloadView();

		return pageTitle;
	}

}
