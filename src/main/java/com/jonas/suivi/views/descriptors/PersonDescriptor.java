package com.jonas.suivi.views.descriptors;

import com.jonas.suivi.backend.model.impl.Person;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;

@MainEntity(Person.class)
public class PersonDescriptor extends Application {

		public static final String appPath = "persons";
	
	public PersonDescriptor() {
		super();
		this.setAppLabelKey(EAppTranslation.APP_LABEL_PERSON.name());
		this.setAppName("persons");
		this.mainEntity = PersonDescriptor.class.getAnnotation(MainEntity.class).value().getSimpleName();
		this.appPathLoc = appPath;
		
		
		FieldDetail loginField = new FieldDetail();
		loginField.setName("login");
		loginField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LOGIN.name());

		FieldDetail nameField = new FieldDetail();
		nameField .setName("lastName");
		nameField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LAST_NAME.name());
		
		FieldDetail firstNameField = new FieldDetail();
		firstNameField.setName("firstName");
		firstNameField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_FIRST_NAME.name());

		FieldDetail mailField = new FieldDetail();
		mailField.setName("email");
		mailField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_MAIL_NAME.name());
	
		
		this.setDlManager(DetailLayoutManager.createSimpleDetail(loginField, nameField, firstNameField, mailField));
		
		
	}
	
	
}
