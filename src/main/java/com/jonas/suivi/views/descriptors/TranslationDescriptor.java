package com.jonas.suivi.views.descriptors;

import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.views.model.Action;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.TableLayoutManager;



@MainEntity(Translation.class)
public class TranslationDescriptor extends Application {

	public TranslationDescriptor() {
		

		this.setAppLabelKey(EAppTranslation.APP_LABEL_TRANSLATION.name());
		this.setAppName("translations");
		
		
		FieldDetail key = new FieldDetail();
		key.setType(Input.SELECT);
		key.setValueProviders(Arrays.asList(EAppTranslation.class, EAppFieldsTranslation.class));
		key.setName("key");
		key.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_KEY.name());
		
		FieldDetail transLatedValueFrench = new FieldDetail();
		transLatedValueFrench.setName("frenchValue");
		transLatedValueFrench.setType(Input.TEXT_INPUT);
		transLatedValueFrench.setTranslationKey(EAppFieldsTranslation.FRENCH.name());
		
		FieldDetail transLatedValueEnglish = new FieldDetail();
		transLatedValueEnglish.setName("englishValue");
		transLatedValueEnglish.setType(Input.TEXT_INPUT);
		transLatedValueEnglish.setTranslationKey(EAppFieldsTranslation.ENGLISH.name());
		
		Action refreshContext = new Action();
		refreshContext.setActionType(ActionType.SUBMIT);
		refreshContext.setBefore(false);
		refreshContext.setServiceAction(g -> g.reloadContext());
		
		setAction(Arrays.asList(refreshContext));
//		});
//		Arrays.sort(a, c);
				
				//		refreshContext.getServiceAction().accept(DisplayableService::reloadContext);
		
//		transLatedValue.setKeyProvider(ESupportedLocales.class);
		
		
		
		setDlManager(DetailLayoutManager.createSimpleDetail(key, transLatedValueEnglish, transLatedValueFrench));
//		TableLayoutManager
		
		TableLayoutManager lManager = new TableLayoutManager();
//		lManager.
		
		
		
//		labelField.setName("name");
//		labelField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LABEL.name());
//		
//		FieldDetail description = new FieldDetail();
//		description.setType(Input.TEXT_AREA);
//		description.setName("description");
//		description.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_DESCRIPTION.name());
//
//		FieldDetail projectManager = new FieldDetail();
//		projectManager.setType(Input.SELECT);
//		projectManager.setName("projectManager");
//		projectManager.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_MANAGER.name());
//		projectManager.setEntityDescriptor(PersonDescriptor.class);
		
	}
	
}
