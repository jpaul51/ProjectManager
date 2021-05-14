package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Application  implements Serializable{

	
	String appName;
	String appLabelKey;
	
	DetailLayoutManager dlManager;
	TableLayoutManager tlManager;
	
	
    List<Action> action = new ArrayList<>();
  
    
    public Application() {
    	tlManager = new TableLayoutManager();
	}
    
	private boolean noTable;


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public String getAppLabelKey() {
		return appLabelKey;
	}


	public void setAppLabelKey(String appLabel) {
		this.appLabelKey = appLabel;
	}


	public DetailLayoutManager getDlManager() {
		return dlManager;
	}


	public void setDlManager(DetailLayoutManager dlManager) {
		this.dlManager = dlManager;
	}


	public TableLayoutManager getTlManager() {
		return tlManager;
	}


	public void setTlManager(TableLayoutManager tlManager) {
		this.tlManager = tlManager;
	}


	public List<Action> getAction() {
		return action;
	}


	public void setAction(List<Action> action) {
		this.action = action;
	}


	public void setNoTable() {
		noTable = true;
	}
	
	public boolean isNoTable() {
		return noTable;
	}
	
    public List<FieldDetail> getAllFields(){
    	
    	List<FieldDetail> fieldList = new ArrayList<>();
    	
    	for(Bloc  bloc: dlManager.getDefaultDetail().getBlocs()) {
    		for(Line line : bloc.getLines()) {
    			for(FieldDetail field : line.getFields()) {
    				fieldList.add(field);
    			}
    		}
    	}
    	return new ArrayList<>(fieldList);
    }
    
	public void addAction(Action a) {
		action.add(a);
	}
	
}
