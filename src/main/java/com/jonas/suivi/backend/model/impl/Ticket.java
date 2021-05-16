package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class Ticket implements Displayable, Serializable{
	
	@Id @GeneratedValue
	private Long id;
	
	private String title;
	private String description;
	private Project project;
	private Person reporter;	
	private LocalDateTime createdDate;
	
	@OneToMany
	private List<TicketNote> notes;
	
	private ETicketStatus ticketStatus;
	private ETicketType ticketType;
	private ETicketPriority ticketPriority;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Person getReporter() {
		return reporter;
	}
	public void setReporter(Person reporter) {
		this.reporter = reporter;
	}
	
	
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public List<TicketNote> getNotes() {
		return notes;
	}
	public void setNotes(List<TicketNote> notes) {
		this.notes = notes;
	}
	public ETicketStatus getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(ETicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public ETicketType getTicketType() {
		return ticketType;
	}
	public void setTicketType(ETicketType ticketType) {
		this.ticketType = ticketType;
	}
	public ETicketPriority getTicketPriority() {
		return ticketPriority;
	}
	public void setTicketPriority(ETicketPriority ticketPriority) {
		this.ticketPriority = ticketPriority;
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String getLabel() {
		return getTitle();
	}
	
	
	@Override
	public void setSimpleValue(String value) {
		this.setTitle(value);
	}
	
	
	
	
	

}
