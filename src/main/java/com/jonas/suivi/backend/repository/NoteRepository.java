package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.impl.Note;
import com.jonas.suivi.backend.model.impl.TicketNote;

public interface NoteRepository extends JpaRepository<TicketNote, Long>{

	public default void saveOne(TicketNote d) {
		save(d);
	}

}
