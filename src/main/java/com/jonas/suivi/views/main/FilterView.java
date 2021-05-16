package com.jonas.suivi.views.main;

import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Application;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@com.vaadin.flow.component.Tag(value = "filter-view")
public class FilterView extends Component {

	public FilterView(Application app) {
		//TODO: filter view
		Div mainDiv = new Div();

		VerticalLayout vl = new VerticalLayout();

		app.getAllFields().stream().forEach(f -> {

			TranslationUtils.translate(f.getTranslationKey());
			f.getType();
		});

	}

}
