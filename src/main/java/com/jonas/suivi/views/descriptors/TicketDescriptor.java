package com.jonas.suivi.views.descriptors;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.ETicketPriority;
import com.jonas.suivi.backend.model.impl.ETicketStatus;
import com.jonas.suivi.backend.model.impl.ETicketType;
import com.jonas.suivi.backend.model.impl.Ticket;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.TableLayoutManager;

@MainEntity(Ticket.class)
public class TicketDescriptor extends Application {
	
	public TicketDescriptor() {
	super();
	this.setAppLabelKey(TranslationUtils.translate(EAppTranslation.APP_LABEL_TICKET.name()));

	
	FieldDetail titleField = new FieldDetail();
	titleField.setType(Input.TEXT_INPUT);
	titleField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_TITLE.name());
	titleField.setName("title");
	
	FieldDetail descField = new FieldDetail();
	descField.setType(Input.TEXT_RICH);
	descField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());
	descField.setName("description");
	
	FieldDetailList projectField = new FieldDetailList();
	projectField.setTranslationKey(EAppTranslation.APP_LABEL_PROJECT.name());
	projectField.setName("project");
	projectField.setEntityDescriptor(ProjectDescriptor.class);
	

	FieldDetailList ticketReporter = new FieldDetailList();
	ticketReporter.setName("reporter");
	ticketReporter.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_REPORTER.name());
	ticketReporter.setEntityDescriptor(PersonDescriptor.class);
	
	
	FieldDetail createdDate = new FieldDetail();
	createdDate.setType(Input.DATE_TIME);
	createdDate.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_CREATED_DATE.name());
	createdDate.setName("createdDate");
	createdDate.setReadOnly(true);
	createdDate.setDefaultValue(LocalDateTime::now);

	FieldDetailList ticketStatus = new FieldDetailList();
	ticketStatus.setValueProviders(ETicketStatus.class);		
	ticketStatus.setName("ticketStatus");
	ticketStatus.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_STATUS.name());
	ticketStatus.setEntityDescriptor(TicketDescriptor.class);

	FieldDetailList ticketType= new FieldDetailList();
	ticketType.setValueProviders(ETicketType.class);		
	ticketType.setName("ticketType");
	ticketType.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_TYPE.name());
	ticketType.setEntityDescriptor(TicketDescriptor.class);
	
	FieldDetailList ticketPriority= new FieldDetailList();
	ticketPriority.setValueProviders(ETicketPriority.class);		
	ticketPriority.setName("ticketPriority");
	ticketPriority.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_PRIORITY.name());
	ticketPriority.setEntityDescriptor(TicketDescriptor.class);

	FieldDetailList notetField = new FieldDetailList(true);
	notetField .setTranslationKey(EAppTranslation.APP_LABEL_PROJECT.name());
	notetField .setName("notes");
	notetField .setEntityDescriptor(TicketNoteDescriptor.class);
	notetField.setUnique(false);
	
	
	
	
	this.setDlManager(DetailLayoutManager.createSimpleDetail(titleField, descField, ticketReporter, projectField, ticketPriority, createdDate, notetField));
	

	
	TableLayoutManager tbl = new TableLayoutManager();
	tbl.getDefaultResultView().getQuickSearchList().addAll(Arrays.asList(titleField, descField));
	SortField sort = new SortField();
	sort.addSort(ticketPriority);
	tbl.getDefaultResultView().setSortField(sort);
	tbl.getDefaultResultView().getColumns().addAll(Arrays.asList(titleField, ticketPriority,projectField, ticketStatus, ticketReporter ));
	
	
	
	this.setTlManager(tbl);
	}
	
}
