package com.jonas.suivi.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.TicketNote;
import com.jonas.suivi.backend.repository.NoteRepository;



@Service("TicketNote")
public class TicketNoteService implements DisplayableService {

	
	@Autowired
	NoteRepository noteRepo;

	@Override
	public <TicketNote extends Displayable> List<TicketNote> getAll() {
		return (List<TicketNote>) noteRepo.findAll();
	}

	@Override
	public <I extends Displayable> void update(I d) {
		noteRepo.saveOne( (TicketNote) d);
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		noteRepo.delete((TicketNote) d);
	}
	
//	@Override
//	public <T extends Displayable> List<T> getWithSorting(SortField sort) {
//
//		List<Sort> sortList = new ArrayList<>();
//		int i = 0;
//		sort.getSortingByField().forEach(m ->{
//			Iterator<Entry<FieldDetail,SortType>> entrySetIterator = m.entrySet().iterator();
//			while(entrySetIterator.hasNext()) {
//				Entry<FieldDetail,SortType> entry = entrySetIterator.next();
//				entry.getKey().getName();	
//				entry.getValue().name();
//				if(sortList.size() <= i ) {
//					sortList.add(Sort.by(entry.getKey().getName()));
//				}
//				sortList.set(i,sortList.get(i).descending());
////				sortdb.by(entry.getKey().getName()).descending();
//			}
//		});
//		
//		
//		return (List<T>) noteRepo.findAll(sortList.get(i));
//	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		return (JpaRepository<T, Long>) noteRepo;
	}

	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		return (Optional<T>) (noteRepo.findById(Long.parseLong(d)));
	}
	
}
