package com.jonas.suivi.views.components;

import com.jonas.suivi.backend.model.Displayable;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.select.Select;

public interface ComponentVisitor {

	
	public String visit(String a);
	public Displayable visit(Select<Displayable> d, Displayable obj);
	Displayable visit(ComboBox<Displayable> select, Displayable value);
	
}
