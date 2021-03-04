package com.jonas.suivi.backend.model.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class Ticket implements Displayable{
	
	@Id @GeneratedValue
	private Long id;
	
	private String title;
	private String description;
	private Project project;
	private Person reporter;	
	private DateTime createdDate;
	
	@OneToMany
	private List<Note> notes;
	
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
	public DateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
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
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
		result = prime * result + ((ticketPriority == null) ? 0 : ticketPriority.hashCode());
		result = prime * result + ((ticketStatus == null) ? 0 : ticketStatus.hashCode());
		result = prime * result + ((ticketType == null) ? 0 : ticketType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (reporter == null) {
			if (other.reporter != null)
				return false;
		} else if (!reporter.equals(other.reporter))
			return false;
		if (ticketPriority != other.ticketPriority)
			return false;
		if (ticketStatus != other.ticketStatus)
			return false;
		if (ticketType != other.ticketType)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String getLabel() {
		return getTitle();
	}
	
	
	
	
	
	
	
	

}
