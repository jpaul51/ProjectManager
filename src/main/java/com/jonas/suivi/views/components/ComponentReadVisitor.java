package com.jonas.suivi.views.components;

import com.jonas.suivi.backend.model.Displayable;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.select.Select;

public class ComponentReadVisitor implements ComponentVisitor{

	@Override
	public String visit(String s) {
		return s;
	}



	@Override
	public Displayable visit(Select<Displayable> d, Displayable obj) {
		return d.getValue();
	}



	@Override
	public Displayable visit(ComboBox<Displayable> select, Displayable value) {
		return select.getValue();
	}

}
