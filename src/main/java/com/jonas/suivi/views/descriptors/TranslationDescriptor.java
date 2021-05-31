package com.jonas.suivi.views.descriptors;

import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.views.model.Action;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.FieldDetailList;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.TableLayoutManager;



@MainEntity(Translation.class)
public class TranslationDescriptor extends Application {

	public static final String appPAth = "translations";
	
	public TranslationDescriptor() {
		super();

		this.setAppLabelKey(EAppTranslation.APP_LABEL_TRANSLATION.name());
		this.setAppName("translations");
		this.mainEntity = TranslationDescriptor.class.getAnnotation(MainEntity.class).value().getSimpleName();
		this.appPathLoc = appPath;
		
		FieldDetailList key = new FieldDetailList();
		key.setValueProviders(EAppTranslation.class, EAppFieldsTranslation.class);		
		key.setName("key");
		key.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_KEY.name());
		key.setEntityDescriptor(TranslationDescriptor.class);
		
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

		
		setDlManager(DetailLayoutManager.createSimpleDetail(key, transLatedValueEnglish, transLatedValueFrench));
		
		TableLayoutManager lManager = new TableLayoutManager();

		
	}
	
}
