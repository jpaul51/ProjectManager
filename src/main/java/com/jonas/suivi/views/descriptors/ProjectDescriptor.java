package com.jonas.suivi.views.descriptors;


import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.Project;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.jonas.suivi.views.model.ResultView;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.TableLayoutManager;




@MainEntity(Project.class)
public class ProjectDescriptor extends Application{

	public static final String appPath = "projects";
	
	public ProjectDescriptor() {
		super();
		this.setAppLabelKey(EAppTranslation.APP_LABEL_PROJECT.name());
		this.setAppName("projects");
		this.mainEntity = ProjectDescriptor.class.getAnnotation(MainEntity.class).value().getSimpleName();
		this.appPathLoc = appPath;
		
		
		FieldDetail labelField = new FieldDetail();
		labelField.setType(Input.TEXT_INPUT);
		labelField.setName("name");
		labelField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LABEL.name());
		
		FieldDetail description = new FieldDetail();
		description.setType(Input.TEXT_AREA);
		description.setName("description");
		description.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());

		FieldDetailList projectManager = new FieldDetailList();
		projectManager.setName("projectManager");
		projectManager.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_MANAGER.name());
		projectManager.setEntityDescriptor(PersonDescriptor.class);
		
		FieldDetail creationDate = new FieldDetail();
		creationDate .setType(Input.DATE);
		creationDate.setName("creationDate");
		creationDate.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_CREATED_DATE.name());
		
		Line headLine = new Line();
		headLine.setFields(Arrays.asList(labelField, description, projectManager, creationDate));
		
		
		Bloc bHeader = new Bloc();
		bHeader.setLines(Arrays.asList(headLine));
		
		DetailLayoutManager dl = new DetailLayoutManager();
		Detail defaultDetail = new Detail();
		defaultDetail.setBlocs(Arrays.asList(bHeader));
		
		dl.setDefaultDetail(defaultDetail);
		
		TableLayoutManager tl = new TableLayoutManager();
		
		ResultView defaultResultView = new ResultView();
		defaultResultView.setLabelKey("Projets actif");
		defaultResultView.setLinesPerPage(10);
		defaultResultView.setQuickSearchList(labelField, description);
		
		SortField sf = new SortField();
		sf.addSort(creationDate);
		defaultResultView.setSortField(sf);
		
		defaultResultView.setColumns(getAllFields());
		
		
		tl.setDefaultResultView(defaultResultView);
		
		
		
		this.setDlManager(dl);
		
	}
	
}
