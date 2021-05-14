package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class DetailLayoutManager  implements Serializable {
	
	
	Detail defaultDetail;
	
	Set<Detail> details = new HashSet<Detail>();

	
	public DetailLayoutManager() {
		defaultDetail = new Detail();
		details = new HashSet<>();
	}
	
	public Detail getDefaultDetail() {
		return defaultDetail;
	}

	public void setDefaultDetail(Detail defaultDetail) {
		this.defaultDetail = defaultDetail;
		details.add(defaultDetail);
	}

	public Set<Detail> getDetails() {
		return details;
	}

	public void setDetails(Set<Detail> details) {
		this.details = details;
	}
	
	public static DetailLayoutManager createSimpleDetail(FieldDetail... fields ) {
	
		DetailLayoutManager dlm = new DetailLayoutManager();
		Detail defaultDetail = new Detail();
		dlm.setDefaultDetail(defaultDetail);
		dlm.getDetails().add(defaultDetail);
		
		Bloc bloc = new Bloc();
		Line line = new Line(fields);
		bloc.getLines().add(line);
		defaultDetail.getBlocs().add(bloc);
		
		return dlm;
	}
	

	
	
}
