package com.jonas.suivi.backend.model.impl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class TicketNote extends Note implements Displayable {

	@ManyToOne
	private Ticket ticket;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String getLabel() {
		return this.getData();
	}
	
	
}