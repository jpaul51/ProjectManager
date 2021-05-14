package com.jonas.suivi.views.descriptors;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.TicketNote;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.jonas.suivi.views.model.ResultView;
import com.jonas.suivi.views.model.TableLayoutManager;

@MainEntity(TicketNote.class)
public class TicketNoteDescriptor extends Application{

	public TicketNoteDescriptor() {
		super();
		
		this.setAppLabelKey(TranslationUtils.translate(EAppTranslation.APP_LABEL_NOTE.name()));
		this.mainEntity = TicketNoteDescriptor.class.getAnnotation(MainEntity.class).value().getSimpleName();

		FieldDetail dataField = new FieldDetail();
		dataField.setType(Input.TEXT_RICH);
		dataField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());
		dataField.setName("data");
		
		FieldDetailList authorField = new FieldDetailList();
		authorField.setName("author");
		authorField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_AUTHOR.name());
		authorField.setEntityDescriptor(PersonDescriptor.class);
		
		FieldDetail createdDate = new FieldDetail();
		createdDate.setType(Input.DATE_TIME);
		createdDate.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_CREATED_DATE.name());
		createdDate.setName("createdDate");
		createdDate.setReadOnly(true);
		createdDate.setDefaultValue(LocalDateTime::now);
		
		DetailLayoutManager dlm = new DetailLayoutManager();
		this.setDlManager(dlm);
		
		Detail defaultDetail = new Detail();
		Bloc b = new Bloc();
		Line l = new Line(dataField, authorField);
		Line dateLine = new Line(createdDate);
		dateLine.setHidden(true);
		b.setLines(Arrays.asList(l, dateLine));		
		defaultDetail.getBlocs().add(b);
		dlm.setDefaultDetail(defaultDetail);
		
		TableLayoutManager tlm = new TableLayoutManager();
		this.setTlManager(tlm);
		
		ResultView rv = new ResultView();
		rv.getColumns().addAll(Arrays.asList(authorField, dataField, createdDate));
		rv.getQuickSearchList().add(dataField);
		
		
		
		
	}
	
	
}
