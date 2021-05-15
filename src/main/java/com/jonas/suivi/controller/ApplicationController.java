package com.jonas.suivi.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.SimpleDisplayable;
import com.jonas.suivi.backend.services.ApplicationService;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.model.Application;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class ApplicationController {

	private String entityClassPath = "com.jonas.suivi.backend.model.impl.";

	@Autowired
	ServiceProxy serviceProxy;

	@Autowired
	ApplicationService appService;

	@RequestMapping(method = RequestMethod.GET, value = Application.appPath + ControllerConstants.END_URI_DESCRIPTOR)
	public @ResponseBody ResponseEntity<List<Application>> appConfig() {

		List<Application> apps = new ArrayList<>();
		apps = appService.getAppList();

		return ResponseEntity.ok(apps);
	}

	@RequestMapping(method = RequestMethod.GET, value = Application.appPath)
	public @ResponseBody ResponseEntity<List<Displayable>> getAll(@RequestParam("entity") String entityClazzName) {

		Class<? extends Displayable> entityClazz;
		try {
			entityClazz = (Class<? extends Displayable>) Class.forName(entityClassPath + entityClazzName);
			return ResponseEntity.ok(serviceProxy.getInstance(entityClazz).getAll());

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unknown entity: " + entityClazzName);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = Application.appPath + "Provider")
	public @ResponseBody ResponseEntity<List<Displayable>> getProvider(
			@RequestParam("descriptor") String descriptorName, @RequestBody(required = false) List<String> providers) {

		List<Displayable> dataFromDescriptor = appService.getDataFromDescriptor(descriptorName);
		List<Displayable> keys = new ArrayList<>();

		Set<? extends Enum> allElementsInMyEnum = new HashSet<>();
		if (providers != null) {
			providers.stream().forEach(p -> {
				Class enumClass = null;
				try {
					enumClass = Class.forName(p);
					allElementsInMyEnum.addAll(EnumSet.allOf(enumClass));

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			});

			Iterator<? extends Enum> keyIterator = allElementsInMyEnum.iterator();
			while (keyIterator.hasNext()) {
				Enum en = keyIterator.next();
				if (!dataFromDescriptor.stream().anyMatch(d -> d.getLabel().equals(en.name()))) {
					SimpleDisplayable t = new SimpleDisplayable(en.name());
					keys.add(t);
				}

			}

		}

		return ResponseEntity.ok(keys);

	}

	@RequestMapping(method = RequestMethod.GET, value = Application.appPath + "Page")
	public @ResponseBody Page<Displayable> get(@RequestParam("descriptor") String descriptorName, @RequestParam String filter,
			@RequestParam int pageIndex, @RequestParam int pageSize) {

		Optional<Application> optApp = appService.getApplicationFromDescriptorName(descriptorName);
		Application app = null;

		if (optApp.isEmpty()) {
			throw new RuntimeException("Invalid descriptor name");
		}
		app = optApp.get();
		Class<Displayable> entityClazz = appService.getEntityFromApp(app);

		Example<Displayable> exampleFilter = appService.generateExampleFromFilters(filter, app, entityClazz);

		return (Page<Displayable>) serviceProxy.getInstance(entityClazz).getWithExampleAndSorting(exampleFilter,
				app.getTlManager().getDefaultResultView().getSortField(), pageIndex, pageSize);
	}

}
