package com.jonas.suivi.backend.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Intervention;
import com.jonas.suivi.backend.repository.InterventionRepository;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.SortField.SortType;

@Service("Intervention")
public class InterventionService implements DisplayableService {

	@Autowired
	InterventionRepository interRepo;

	@Override
	public <Intervention extends Displayable> List<Intervention> getAll() {
		return (List<Intervention>) interRepo.findAll();
	}

	@Override
	public <I extends Displayable> void update(I d) {
		interRepo.saveOne((Intervention) d);
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		interRepo.delete((Intervention) d);
	}

	@Override
	public <T extends Displayable> List<T> getWithSorting(SortField sort) {

		List<Sort> sortList = new ArrayList<>();
		int i = sort.getSortList(sortList);
//		interRepo.findAll(PageRequest.of(1, 10));
		return (List<T>) interRepo.findAll(sortList.get(i));
	}



	@Override
	public <T extends Displayable> Page<T> getWithExampleAndSorting(Example example, SortField sort, int page,
			int size) {
		List<Sort> sortList = new ArrayList<>();
		int i = sort.getSortList(sortList);
		return (Page<T>) getRepo().findAll(example, PageRequest.of(page, size, sortList.get(i)));
	}

	@Override
	public <T extends Displayable> Page<T> getWithSorting(SortField sort, int page, int size) {
		List<Sort> sortList = new ArrayList<>();
		int i = sort.getSortList(sortList);

		return (Page<T>) getRepo().findAll(PageRequest.of(page, size, sortList.get(i)));

	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		// TODO Auto-generated method stub
		return (JpaRepository<T, Long>) interRepo;
	}

	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		// TODO Auto-generated method stub
		return (Optional<T>) (interRepo.findById(Long.parseLong(d)));
	}

}
