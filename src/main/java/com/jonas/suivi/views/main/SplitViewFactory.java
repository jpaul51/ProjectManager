package com.jonas.suivi.views.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jonas.suivi.UserContextFactory;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Application;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.router.QueryParameters;

public class SplitViewFactory {

	@Autowired
	UserContextFactory userContextFactory;

	ArrayList<Column> columns = new ArrayList<Grid.Column>();
	SplitView dv;
	Class<? extends Application> clazz;

	public SplitViewFactory(Class<? extends Application> pclazz) {
		clazz = pclazz;
	}

	public void show() {
		Map<String, List<String>> mapParams = new HashMap<>();
		mapParams.put("clazz", Arrays.asList(clazz.getCanonicalName()));

		QueryParameters q = new QueryParameters(mapParams);

		Class<? extends Application> ctxClazz = UserContextFactory.getCurrentUserContext().getCurrentClass();

		UserContextFactory.getCurrentUserContext().setCurrentClass(clazz);

		UI.getCurrent().navigate("app", q);

	}

	public String getAppTitle() {
		try {
			return TranslationUtils.translate(clazz.getConstructor().newInstance().getAppLabelKey());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "HOME";
		}

	}

}
