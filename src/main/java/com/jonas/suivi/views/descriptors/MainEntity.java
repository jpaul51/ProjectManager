package com.jonas.suivi.views.descriptors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.jonas.suivi.backend.model.Displayable;

@Retention(RetentionPolicy.RUNTIME)
public @interface MainEntity {

	public Class<? extends Displayable> value();
	
}
