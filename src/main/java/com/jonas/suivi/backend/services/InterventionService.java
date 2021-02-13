package com.jonas.suivi.backend.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		interRepo.saveOne( (Intervention) d);
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		interRepo.delete((Intervention) d);
	}
	
	@Override
	public <T extends Displayable> List<T> getWithSorting(SortField sort) {
//		sort.getSortingByField();
		sort.getSortingByField().forEach(m ->{
			Iterator<Entry<FieldDetail,SortType>> entrySetIterator = m.entrySet().iterator();
			while(entrySetIterator.hasNext()) {
				Entry<FieldDetail,SortType> entry = entrySetIterator.next();
//				entry.getKey().getName()gin
			}
		});
		
		return (List<T>) interRepo.findAll(Sort.by("id").descending());
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
