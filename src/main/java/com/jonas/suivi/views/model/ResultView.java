package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultView implements Serializable {


	List<FieldDetail> columns = new ArrayList<>();
    Set<FieldDetail> quickSearchList = new HashSet<>();

	SortField sortField = new SortField();
	
	
	public ResultView() {

	
	}
	
	
	public void addColumn(FieldDetail col) {
		this.columns.add(col);
	}


	public List<FieldDetail> getColumns() {
		return columns;
	}


	public void setColumns(List<FieldDetail> columns) {
		this.columns = columns;
	}


	public Set<FieldDetail> getQuickSearchList() {
		return quickSearchList;
	}


	public void setQuickSearchList(Set<FieldDetail> quickSearchList) {
		this.quickSearchList = quickSearchList;
	}


	public SortField getSortField() {
		return sortField;
	}


	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}
	
	
	
	
	
	
	
}
