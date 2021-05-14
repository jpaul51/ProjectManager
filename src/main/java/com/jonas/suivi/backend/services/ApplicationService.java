package com.jonas.suivi.backend.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.controller.ESupportedApplications;
import com.jonas.suivi.views.descriptors.MainEntity;
import com.jonas.suivi.views.model.Application;

@Service
public class ApplicationService {

	@Autowired
	ServiceProxy serviceProxy;

	public List<Application> getAppList() {
		ArrayList<Application> apps = new ArrayList<>();
		Stream.of(ESupportedApplications.values()).forEach(descClass -> {
			try {
				Application app = descClass.getDescriptor().getConstructor().newInstance();
				apps.add(app);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return apps;
	}

	public List<Displayable> getDataFromDescriptor(String descriptorName) {

		List<Displayable> data = null;
		Optional<Application> optApp = getAppList().stream()
				.filter(app -> app.getClass().getSimpleName().equals(descriptorName)).findFirst();

		if(optApp.isPresent()) {
			Class entity = optApp.get().getClass().getAnnotation(MainEntity.class).value();
			data = serviceProxy.getInstance(entity).getAll();
		}
		
		return data;

	}

}
