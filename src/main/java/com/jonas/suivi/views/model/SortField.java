package com.jonas.suivi.views.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Sort;

import com.jonas.suivi.views.model.SortField.SortType;

public class SortField implements Serializable {

	
	
	public enum SortType{
		ASC,
		DESC
	}
	
	List<Map<FieldDetail, SortType>> sortingByField = new ArrayList<>();
	
	
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

	public int getSortList(List<Sort> sortList) {
		int i = 0;
		getSortingByField().forEach(m -> {
			Iterator<Entry<FieldDetail, SortType>> entrySetIterator = m.entrySet().iterator();
			while (entrySetIterator.hasNext()) {
				Entry<FieldDetail, SortType> entry = entrySetIterator.next();
				entry.getKey().getName();
				entry.getValue().name();
				if (sortList.size() <= i) {
					sortList.add(Sort.by(entry.getKey().getName()));
				}
				sortList.set(i, sortList.get(i).descending());
	//				sortdb.by(entry.getKey().getName()).descending();
			}
		});
		return i;
	}
	
	
	
	
}
