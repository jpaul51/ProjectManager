package com.jonas.suivi.backend.services;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.SortField.SortType;

public interface SearchInterface {

	public default int getSortList(SortField sort, List<Sort> sortList) {
		int i = 0;
		sort.getSortingByField().forEach(m -> {
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

	public default Example<Displayable> generateExampleFromFilters(String filter, Application app,
			Class<Displayable> entityClazz) {
		Example<Displayable> filterExample = null;

		try {
			Displayable currentExample = entityClazz.getConstructor().newInstance();

			ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING);

			app.getTlManager().getDefaultResultView().getQuickSearchList().forEach(filterField -> {
				PropertyDescriptor pd;
				try {
					pd = new PropertyDescriptor(filterField.getName(), entityClazz);
					pd.getWriteMethod().invoke(currentExample, filter);

					exampleMatcher.withMatcher(filterField.getName(),
							ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

				} catch (IntrospectionException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}

//						filterField.getName()
			});
			filterExample = Example.of(currentExample, exampleMatcher);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return filterExample;
	}

}