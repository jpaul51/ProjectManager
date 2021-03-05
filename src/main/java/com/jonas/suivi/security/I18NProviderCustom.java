package com.jonas.suivi.security;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.descriptors.ESupportedLocales;
import com.vaadin.flow.i18n.I18NProvider;

@Component
public class I18NProviderCustom implements I18NProvider{

	@Override
	public List<Locale> getProvidedLocales() {

		List<Locale> locales = Arrays.asList(ESupportedLocales.values()).stream().map(ESupportedLocales::getLocale).collect(Collectors.toList());
		
		return locales;
	}

	@Override
	public String getTranslation(String key, Locale locale, Object... params) {
		// TODO Auto-generated method stub
		return TranslationUtils.translate(key);
	}

}
