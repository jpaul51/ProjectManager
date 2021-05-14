package com.jonas.suivi.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.views.model.SortField;

public interface DisplayableService {

	public <T extends Displayable> List<T> getAll();

	public <T extends Displayable> void update(T d);

	public <T extends Displayable> void delete(T d);

	public default <T extends Displayable> void create(T d) {
		update(d);
	}

	public <T extends Displayable> Optional<T> getByIdentifier(String d);

	public default <T extends Displayable> List<T> getWithSorting(SortField sort) {
//		sort.getSortingByField();			
		return (List<T>) getRepo().findAll(Sort.by("id").descending());

	}
	
	public default <T extends Displayable> Page<T> getWithSorting(SortField sort, int page, int size) {
		return  (Page<T>) getRepo().findAll(PageRequest.of(page, size, Sort.by("id").descending()));

	}

	public default <T extends Displayable> List<T> getWithExampleAndSorting( Example example, SortField sort ){
//		sort.getSortingByField();			
		return (List<T>) getRepo().findAll(example, Sort.by("id").descending());

	}
	
	public default <T extends Displayable> Page<T> getWithExampleAndSorting( Example example, SortField sort, int page, int size ){
//		sort.getSortingByField();			
		return (Page<T>) getRepo().findAll(example, PageRequest.of(page, size, Sort.by("id").descending()));

	}

	public <T extends Displayable> JpaRepository<T, Long> getRepo();

	/**
	 * Used for refreshing context data from newly inserted values For example:
	 * reload translation list after edition
	 */
	default public void reloadContext() {

	}

}
