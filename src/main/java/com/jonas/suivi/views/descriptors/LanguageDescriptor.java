package com.jonas.suivi.views.descriptors;

import com.jonas.suivi.views.model.Application;

public class LanguageDescriptor extends Application{
	
	public static final String appPath = "language";

	public LanguageDescriptor() {
		super();
		setAppLabelKey(EAppTranslation.APP_LABEL_LANGUAGE.name());
		setAppName("languages");
		this.appPathLoc = LanguageDescriptor.appPath;

		
	}
	
}
