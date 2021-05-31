package com.jonas.suivi.views.descriptors;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.Intervention;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Action;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.TableLayoutManager;

@MainEntity(Intervention.class)
public class InterventionDescriptor extends Application{

	public final static String appPath = "interventions";
	
	public InterventionDescriptor() {
		super();
		this.setAppLabelKey(EAppTranslation.APP_LABEL_INTERVENTION.name());
		this.appPathLoc = InterventionDescriptor.appPath;
		this.mainEntity = InterventionDescriptor.class.getAnnotation(MainEntity.class).value().getSimpleName();
		FieldDetail descField = new FieldDetail();
		descField.setType(Input.TEXT_AREA);
		descField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());
		descField.setName("description");
		
		FieldDetail commentField = new FieldDetail();
		commentField.setType(Input.TEXT_RICH);
		commentField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_COMMENT.name());
		commentField.setName("commentaire");
		
		
		FieldDetail createdDate = new FieldDetail();
		createdDate.setType(Input.DATE_TIME);
		createdDate.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_CREATED_DATE.name());
		createdDate.setName("createdDate");
		createdDate.setReadOnly(true);
		createdDate.setDefaultValue(LocalDateTime::now);

		FieldDetail modifiedDate = new FieldDetail();
		modifiedDate .setType(Input.DATE_TIME);
		modifiedDate .setTranslationKey(EAppFieldsTranslation.APP_FIELDS_MODIFIED_DATE.name());
		modifiedDate.setName("lastModifiedDate");
		modifiedDate.setReadOnly(true);
		FunctionalInterfaceLocalDateTime dd = LocalDateTime::now;
		modifiedDate.setDefaultValue(dd);
		
		FieldDetail duration = new FieldDetail();
		duration.setType(Input.DATE_TIME_ONLY);
		duration.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DURATION.name());
		duration.setName("duration");
		
		FieldDetailList projectField = new FieldDetailList();
		projectField.setTranslationKey(EAppTranslation.APP_LABEL_PROJECT.name());
		projectField.setName("project");
		projectField.setEntityDescriptor(ProjectDescriptor.class);
		
		this.setDlManager(DetailLayoutManager.createSimpleDetail(descField, commentField, createdDate, modifiedDate, duration, projectField));
		
		Action onSubmit = new Action();
		onSubmit.setActionType(ActionType.SUBMIT);
		FunctionalInterfaceLocalDateTime computedModifiedDate = LocalDateTime::now;
		onSubmit.addFieldUpdate(modifiedDate, computedModifiedDate);
//		onSubmit.addConsumerUpdate<LocalDateTi>(modifiedDate, t ->);
//		this
		
		this.addAction(onSubmit);
		
		TableLayoutManager tbl = new TableLayoutManager();
		tbl.getDefaultResultView().getQuickSearchList().addAll(Arrays.asList(descField, commentField));
		tbl.getDefaultResultView().setLinesPerPage(10);
		SortField sort = new SortField();
		sort.addSort(modifiedDate);
		tbl.getDefaultResultView().setSortField(sort);
		
		
		
		this.setTlManager(tbl);
		
	}
	
	
	//	
//	
//	private String description;
//	private String commentaire;
//	
//	@ManyToOne
//	private Person owner;
//	private LocalDateTime createdDate;
//	private LocalDateTime lastModifiedDate;
//	private LocalTime duration;
//	@OneToOne
//	private Project project;
	
	
	
}
