package com.jonas.suivi.backend.model.impl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class TicketNote extends Note implements Displayable {


	private static final long serialVersionUID = 7910722885513920217L;
	
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
	@Override
	public void setSimpleValue(String value) {
		this.setData(value);
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	@Override
	public Integer getState() {
		// TODO Auto-generated method stub
		return null;
	}
}
