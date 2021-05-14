package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.impl.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	public default void saveOne(Ticket d) {
		save(d);
	}

}
