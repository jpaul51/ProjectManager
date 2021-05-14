package com.jonas.suivi.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jonas.suivi.backend.model.Displayable;
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

	
	@RequestMapping(method = RequestMethod.GET, value = Application.appPath+"Provider")
	public @ResponseBody ResponseEntity<List<Displayable>> getProvider(@RequestParam("descriptor") String descriptorName,
			@RequestBody(required = false) List<String> providers) {

		List<Displayable> dataFromDescriptor = appService.getDataFromDescriptor(descriptorName);
		
		Set<? extends Enum> allElementsInMyEnum = new HashSet<>(); 
		if(providers != null) {
			providers.stream().forEach(p ->{
				Class enumClass = null;
				try {
					enumClass = Class.forName(p);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				allElementsInMyEnum.addAll(EnumSet.allOf(enumClass));
			});
			
			Iterator<Displayable> dataIterator = dataFromDescriptor.iterator();
			while(dataIterator.hasNext()) {
				Displayable disp = dataIterator.next();
				if(allElementsInMyEnum.stream().filter(en -> en.name().equals(disp.getLabel())).findFirst().isPresent()) {
					dataIterator.remove();
				}
			}
			
		}
		
		return ResponseEntity.ok(dataFromDescriptor);


	}

	
	
	

//	public  Optional<Displayable> getByIdentifier(Class<? extends Displayable> entityClazz, String identifier){
//		return serviceProxy.getInstance(entityClazz).getByIdentifier(identifier);
//	}
//	
//	public  List<Displayable> get(Class<? extends Displayable> entityClazz){
//		return serviceProxy.getInstance(entityClazz).getWithExampleAndSorting(null, null);
//	}

}
