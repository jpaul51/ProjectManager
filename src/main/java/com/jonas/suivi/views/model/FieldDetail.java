package com.jonas.suivi.views.model;

import java.util.List;


public class FieldDetail {

	String type = Input.TEXT_INPUT;
	String name;
	String translationKey;
	
	Class<? extends Application > entityDescriptor;

	List<Class<?  extends Enum>> valueProviders;
	
//	Class<Enum<? extends Enum>> keyProvider;

	
	Boolean readOnly = false;
	boolean hidden = false;
	String fontColor;
	String bgColor;
	
	boolean isValueHidden;
	
	Object defaultValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public Class<? extends Application > getEntityDescriptor() {
		return entityDescriptor;
	}

	public void setEntityDescriptor(Class<? extends Application> entity) {
		this.entityDescriptor = entity;
	}

	

	public List<Class<? extends Enum>> getValueProviders() {
		return valueProviders;
	}

	public void setValueProviders(List<Class<? extends Enum>> valueProviders) {
		this.valueProviders = valueProviders;
	}

	
	
	public boolean isValueHidden() {
		return isValueHidden;
	}

	public void setValueHidden(boolean isValueHidden) {
		this.isValueHidden = isValueHidden;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bgColor == null) ? 0 : bgColor.hashCode());
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((fontColor == null) ? 0 : fontColor.hashCode());
		result = prime * result + (hidden ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((readOnly == null) ? 0 : readOnly.hashCode());
		result = prime * result + ((translationKey == null) ? 0 : translationKey.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDetail other = (FieldDetail) obj;
		if (bgColor == null) {
			if (other.bgColor != null)
				return false;
		} else if (!bgColor.equals(other.bgColor))
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (fontColor == null) {
			if (other.fontColor != null)
				return false;
		} else if (!fontColor.equals(other.fontColor))
			return false;
		if (hidden != other.hidden)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (readOnly == null) {
			if (other.readOnly != null)
				return false;
		} else if (!readOnly.equals(other.readOnly))
			return false;
		if (translationKey == null) {
			if (other.translationKey != null)
				return false;
		} else if (!translationKey.equals(other.translationKey))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
//
//	public Class<Enum<? extends Enum>> getKeyProvider() {
//		return keyProvider;
//	}
//
//	public void setKeyProvider(Class<Enum<? extends Enum>> keyProvider) {
//		this.keyProvider = keyProvider;
//	}

	
	
	
	
	
}
