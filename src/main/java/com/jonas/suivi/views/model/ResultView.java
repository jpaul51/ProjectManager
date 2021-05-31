package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultView implements Serializable {


	List<FieldDetail> columns = new ArrayList<>();
    Set<FieldDetail> quickSearchList = new HashSet<>();

	SortField sortField = new SortField();
	Integer linesPerPage = 5;
	
	
	
	String labelKey;
	
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
	
	public void setColumns(FieldDetail... columns) {
		this.columns.addAll(Arrays.asList(columns));
	}


	public Set<FieldDetail> getQuickSearchList() {
		return quickSearchList;
	}


	public void setQuickSearchList(Set<FieldDetail> quickSearchList) {
		this.quickSearchList = quickSearchList;
	}
	
	public void setQuickSearchList(FieldDetail ...quickSearchList) {
		this.quickSearchList = Set.of(quickSearchList);
	}


	public SortField getSortField() {
		return sortField;
	}


	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}


	public Integer getLinesPerPage() {
		return linesPerPage;
	}


	public void setLinesPerPage(Integer linesPerPage) {
		this.linesPerPage = linesPerPage;
	}


	public String getLabelKey() {
		return labelKey;
	}


	public void setLabelKey(String label) {
		this.labelKey = label;
	}
	
	
	
	
	
	
	
}
