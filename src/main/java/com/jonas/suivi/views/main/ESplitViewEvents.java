package com.jonas.suivi.views.main;

public enum ESplitViewEvents {

	VIEW_STATE("viewstate"), DBL_CLICK("dbl_click");
	
	private String value;
	
	private ESplitViewEvents (String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
