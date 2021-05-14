package com.jonas.suivi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jonas.suivi.backend.model.impl.Intervention;
import com.jonas.suivi.backend.services.InterventionService;
import com.jonas.suivi.views.descriptors.InterventionDescriptor;

@RestController
@RequestMapping(value="/v1")
public class InterventionController {

	
	@Autowired
	InterventionService interService;
	
	public InterventionController() {

	}
	
	@RequestMapping(method = RequestMethod.GET, value = InterventionDescriptor.appPath)
	public List<Intervention> getAllInterventions(){
		return  interService.getAll();
	}
	@RequestMapping(method = RequestMethod.GET, value = InterventionDescriptor.appPath +ControllerConstants.END_URI_DESCRIPTOR)
	public InterventionDescriptor getDataDescriptor(){
		return  new InterventionDescriptor();
	}
	
	  @PostMapping("/interventions")
	  public void newIntervention(@RequestBody Intervention newInter) {
	     interService.update(newInter);
	  }
	
	
}
