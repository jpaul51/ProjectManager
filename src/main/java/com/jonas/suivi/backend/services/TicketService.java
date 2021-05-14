package com.jonas.suivi.backend.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Ticket;
import com.jonas.suivi.backend.repository.TicketRepository;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.SortField;
import com.jonas.suivi.views.model.SortField.SortType;



@Service("Ticket")
public class TicketService implements DisplayableService {

	
	@Autowired
	TicketRepository ticketRepo;

	@Override
	public <Ticket extends Displayable> List<Ticket> getAll() {
		return (List<Ticket>) ticketRepo.findAll();
	}

	@Override
	public <I extends Displayable> void update(I d) {
		ticketRepo.saveOne( (Ticket) d);
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		ticketRepo.delete((Ticket) d);
	}
	
	@Override
	public <T extends Displayable> List<T> getWithSorting(SortField sort) {

		List<Sort> sortList = new ArrayList<>();
		int i = 0;
		sort.getSortingByField().forEach(m ->{
			Iterator<Entry<FieldDetail,SortType>> entrySetIterator = m.entrySet().iterator();
			while(entrySetIterator.hasNext()) {
				Entry<FieldDetail,SortType> entry = entrySetIterator.next();
				entry.getKey().getName();	
				entry.getValue().name();
				if(sortList.size() <= i ) {
					sortList.add(Sort.by(entry.getKey().getName()));
				}
				sortList.set(i,sortList.get(i).descending());
//				sortdb.by(entry.getKey().getName()).descending();
			}
		});
		
		return (List<T>) ticketRepo.findAll(sortList.get(i));
	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		return (JpaRepository<T, Long>) ticketRepo;
	}

	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		return (Optional<T>) (ticketRepo.findById(Long.parseLong(d)));
	}
	
}
