package com.jonas.suivi.views.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HasDynamicTitle;

public abstract class AbstractView extends Div implements AfterNavigationObserver, HasDynamicTitle{

	
	public AbstractView() {
			this.setSizeFull();
		
	}
}
