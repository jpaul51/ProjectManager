package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TableLayoutManager implements Serializable {

	

	ResultView defaultResultView;
	
	Set<ResultView> resultViews = new HashSet<>();
	
	public TableLayoutManager() {

		defaultResultView = new ResultView();
		
	}

	public ResultView getDefaultResultView() {
		return defaultResultView;
	}

	public void setDefaultResultView(ResultView defaultResultView) {
		this.defaultResultView = defaultResultView;
	}

	public Set<ResultView> getResultViews() {
		return resultViews;
	}

	public void setResultViews(Set<ResultView> resultView) {
		this.resultViews = resultView;
	}
	
	
	
	
	
}
