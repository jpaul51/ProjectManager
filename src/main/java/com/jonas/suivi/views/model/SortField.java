package com.jonas.suivi.views.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortField {

	
	
	public enum SortType{
		ASC,
		DESC
	}
	
	List<Map<FieldDetail, SortType>> sortingByField;
	
	
	public SortField() {
		sortingByField = new ArrayList<>();
	}
	
	public void addSort(FieldDetail field) {
		Map<FieldDetail, SortType> map = new HashMap<FieldDetail, SortField.SortType>();
		map.put(field, SortType.DESC);
		sortingByField.add(map);
	}

	public List<Map<FieldDetail, SortType>> getSortingByField() {
		return sortingByField;
	}

	public void setSortingByField(List<Map<FieldDetail, SortType>> sortingByField) {
		this.sortingByField = sortingByField;
	}
	
	
	
	
}
