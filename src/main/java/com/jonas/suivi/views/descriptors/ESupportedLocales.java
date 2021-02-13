package com.jonas.suivi.views.descriptors;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum ESupportedLocales{

	
//	Locale locale;
	
	FRANCE(Locale.FRANCE),
	UK(Locale.UK),
	GERMANY(Locale.GERMANY);
	
	private Locale loc;
	
	public Locale getLocale() {
		return loc;
	}
	
	ESupportedLocales(Locale locale) {
		this.loc = locale;
	}
	
	
	
	
	
}
