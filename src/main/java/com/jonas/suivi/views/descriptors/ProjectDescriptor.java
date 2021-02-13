package com.jonas.suivi.views.descriptors;


import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.Project;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;




@MainEntity(Project.class)
public class ProjectDescriptor extends Application{

	
	
	public ProjectDescriptor() {
		
		this.setAppLabelKey(EAppTranslation.APP_LABEL_PROJECT.name());
		this.setAppName("projects");
		
		
		FieldDetail labelField = new FieldDetail();
		labelField.setType(Input.TEXT_INPUT);
		labelField.setName("name");
		labelField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LABEL.name());
		
		FieldDetail description = new FieldDetail();
		description.setType(Input.TEXT_AREA);
		description.setName("description");
		description.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());

		FieldDetail projectManager = new FieldDetail();
		projectManager.setType(Input.SELECT);
		projectManager.setName("projectManager");
		projectManager.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_MANAGER.name());
		projectManager.setEntityDescriptor(PersonDescriptor.class);
		
		
		Line headLine = new Line();
		headLine.setFields(Arrays.asList(labelField, description, projectManager));
		
		
		Bloc bHeader = new Bloc();
		bHeader.setLines(Arrays.asList(headLine));
		
		DetailLayoutManager dl = new DetailLayoutManager();
		Detail defaultDetail = new Detail();
		defaultDetail.setBlocs(Arrays.asList(bHeader));
		
		dl.setDefaultDetail(defaultDetail);
		
		this.setDlManager(dl);
		
	}
	
}
