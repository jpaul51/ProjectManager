package com.jonas.suivi.views.main;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.Name;
import com.jonas.suivi.backend.services.DisplayableService;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.ResultView;
import com.jonas.suivi.views.model.TableLayoutManager;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;

public class GridView extends SingleView {

	Displayable currentDisplayable;
//	List<Displayable> displayables = new ArrayList<>();
//	List<Displayable> filteredDisplayables = new ArrayList<>();
	private ServiceProxy serviceProxy;
	Grid<Displayable> grid;

	Example forcedExample;

	String pageTitle = "";
	private int currentPage;
	private Integer pageSize;
	private Page<Displayable> displayablePage;
	private String searchedValue = "";
	private Displayable addedItem;

	public GridView(Class<?> context, ServiceProxy serviceProxy, ViewState viewState) {
		this(context, serviceProxy);
		this.currentViewState = viewState;
	}

	public GridView(Class<?> context, ServiceProxy serviceProxy) {
		super(context, serviceProxy);
		this.addClassName("verticalNoPadding");

		if (application.getAppLabelKey() == null) {
			pageTitle = "UNDEFINED";
		} else {
			pageTitle = application.getAppLabelKey();
		}

		if (currentViewState == null) {
			currentViewState = new ViewState(null, EViewEventType.LOAD, viewClazz);
		}
		this.propertyChangeSupport = new PropertyChangeSupport(currentViewState);

		this.serviceProxy = serviceProxy;

		grid = new Grid<>();
		grid.addClassName("my-grid");
		grid.getStyle().set("margin", "var(--lumo-space-m)");
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.setHeight("300px");

		grid.setSizeFull();
		grid.setWidth("98%");

		VerticalLayout vl = initToolbar(grid);
		try {
			createGridClumns(grid);
		} catch (InvalidFieldDescriptorException e) {
			e.printStackTrace();
		}

		vl.add(grid);
		vl.getStyle().set("padding", "0px");
		vl.getStyle().set("overflow-x", "hidden");
		vl.getStyle().set("height", "100%");

		this.pageTitle = TranslationUtils.translate(this.application.getAppLabelKey());

		setGridEvents(grid);
		this.add(vl);
	}

	private void changePage(boolean next) {
		if (next) {
			if (currentPage + 1 < displayablePage.getTotalPages())
				this.currentPage++;
		} else {
			if (currentPage > 0)
				this.currentPage--;
		}
		reloadGrid();
	}

	public void selectItem(Displayable itemToSelect) {
		this.grid.select(itemToSelect);
	}

	public void refreshGrid() {

//		if (this.filteredDisplayables.isEmpty()) {
		List<Displayable> currentPage = this.displayablePage.getContent();

		this.grid.setItems(currentPage);
//		}else {
//			this.grid.setItems(this.filteredDisplayables);

//		}

	}

	@Override
	public void reload() {

		reloadGrid();

	}

	@PostConstruct
	public void reloadGrid() {

		reloadWithFilter(grid, searchedValue);

	}

	private void setGridEvents(Grid<Displayable> grid) {
		grid.addSelectionListener(e -> {
			Optional<Displayable> oDisplayable = e.getFirstSelectedItem();
			if (oDisplayable.isPresent()) {

				final EAPP_CONTEXT currentCtx = appCtx;

				appCtx = EAPP_CONTEXT.UPDATE;

				Displayable selectedDisplayabe = oDisplayable.get();
				e.getSource().select(selectedDisplayabe);

				ViewState openEditViewState = new ViewState(this, EViewEventType.OPEN_EDIT, selectedDisplayabe,
						viewClazz);
				propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), currentViewState,
						openEditViewState);

			}
		});

		GridSingleSelectionModel<Displayable> m = (GridSingleSelectionModel<Displayable>) grid.getSelectionModel();
		m.setDeselectAllowed(false);
	}

	private void createGridClumns(Grid<Displayable> grid) throws InvalidFieldDescriptorException {

		if (this.application == null) {
			UI.getCurrent().navigate("");
		} else {

			TableLayoutManager tlManager = application.getTlManager();

			if (tlManager == null) {

				tlManager = new TableLayoutManager();
			}
			ResultView resultView = tlManager.getDefaultResultView();

			if (resultView.getColumns().isEmpty()) {
				resultView.setColumns(application.getAllFields());
			}

			if (resultView.getLinesPerPage() != null) {
				this.currentPage = 0;
				this.pageSize = resultView.getLinesPerPage();
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

						Optional<Object> valueProvided = createGridColumn(column, disp);

						stringcontent.append(valueProvided.isPresent() ? valueProvided.get() : "")

								.append("</div>");

						return new Html(stringcontent.toString());

					}));
				} else if (column.getType().equals(Input.DATE_TIME)) {
					c = grid.addColumn(new ComponentRenderer<>(disp -> {
						Div d = new Div();
//	        		LocalDateTime
						DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
						formatter = formatter.localizedBy(TranslationUtils.locale);

						Optional<Object> providedValue = createGridColumn(column, disp);

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
					c = grid.addColumn(createGridColumn(column));
				}

				HtmlComponent htmlHeader = new HtmlComponent(Tag.H3);
				htmlHeader.getElement().setText(TranslationUtils.translate(column.getTranslationKey()));
				htmlHeader.addClassName("gridHeader");
				c.setHeader(htmlHeader);

			}
		}
	}

	private ValueProvider<Displayable, ?> createGridColumn(FieldDetail column) {
		return disp -> {

			try {
				String fieldName = column.getName();

				PropertyDescriptor pd = null;

				for (Field field : entityClazz.getDeclaredFields()) {
					if (field.getAnnotation(Name.class) != null) {
						String currfieldName = field.getAnnotation(Name.class).value();
						if (currfieldName.equals(fieldName)) {
							fieldName = currfieldName;
							pd = new PropertyDescriptor(currfieldName, entityClazz);
						}
					}
				}

				if (pd == null) {
					pd = new PropertyDescriptor(fieldName, entityClazz);
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

	private Optional<Object> createGridColumn(FieldDetail column, Displayable disp) {
		String fieldName = column.getName();

		Optional ret;

		PropertyDescriptor pd = null;
		try {
			for (Field field : entityClazz.getDeclaredFields()) {
				if (field.getAnnotation(Name.class) != null) {
					String currfieldName = field.getAnnotation(Name.class).value();
					if (currfieldName.equals(fieldName)) {
						fieldName = currfieldName;
						pd = new PropertyDescriptor(currfieldName, entityClazz);
					}
				}
			}
			if (pd == null) {
				pd = new PropertyDescriptor(fieldName, entityClazz);
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

	private VerticalLayout initToolbar(Grid grid) {
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

			ViewState openEditViewState = new ViewState(this, EViewEventType.OPEN_NEW, viewClazz);
			propertyChangeSupport.firePropertyChange(ESplitViewEvents.VIEW_STATE.getValue(), currentViewState,
					openEditViewState);

			grid.deselectAll();
			grid.asSingleSelect().clear();

		});

		TextField sbox = new TextField();
		sbox.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
		sbox.addKeyPressListener(Key.ENTER, sf -> {
			String searchedValue = sbox.getValue();
			this.searchedValue = searchedValue;
			reloadWithFilter(grid, searchedValue);

		});
		Div searchDiv = new Div();
		searchDiv.add(sbox);
		sbox.focus();
		hl.add(searchDiv);

		Button btnRefresh = new Button(new Icon(VaadinIcon.REFRESH));
		btnRefresh.addClickListener(c -> {
			reloadGrid();
		});
		hl.add(btnRefresh);

		Button btnNextPage = new Button(">");
		btnNextPage.addClickShortcut(Key.PAGE_DOWN);
		btnNextPage.addClickListener(c -> {
			changePage(true);
		});
		Button btnPrevPage = new Button("<");
		btnPrevPage.addClickShortcut(Key.PAGE_UP);
		btnPrevPage.addClickListener(c -> {
			changePage(false);
		});

		hl.add(btnPrevPage, btnNextPage);

		toolbar.getStyle().set("padding", "var(--lumo-space-s) var(--lumo-space-l)");
		toolbar.getStyle().set("border-right", "10px");
		toolbar.getStyle().set("border-color", "#66a8ff");
		VerticalLayout vl = new VerticalLayout();
		vl.add(toolbar);
		return vl;
	}

	private void reloadWithFilter(Grid grid, String searchedValue) {
		if (searchedValue == null || searchedValue.strip().isBlank()) {
			displayablePage = displayableService.getWithSorting(
					application.getTlManager().getDefaultResultView().getSortField(), currentPage, pageSize);
//			filteredDisplayables = new ArrayList<>(displayables);
		} else {

			Example filterExample = serviceProxy.getInstance(entityClazz)
					.generateExampleFromFilters(searchedValue.strip(), application, entityClazz);

			displayablePage = displayableService.getWithExampleAndSorting(filterExample,
					application.getTlManager().getDefaultResultView().getSortField(), currentPage, pageSize);

		}
		grid.setItems(displayablePage.getContent());
		

	}

	public Grid<Displayable> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Displayable> grid) {
		this.grid = grid;
	}

	public List<Displayable> getDisplayables() {
		return displayablePage.getContent();
	}

	public Page<Displayable> getDisplayablePage() {
		return displayablePage;
	}

	public Example getExample() {
		return forcedExample;
	}

	public void setExample(Example example) {
		this.forcedExample = example;
	}

}
