package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class Intervention implements Serializable, Displayable {

	@Id
	@GeneratedValue
	Long id;
	private String description;

	@Column(length = 3000)
	private String commentaire;

	@ManyToOne
	private Person owner;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
	private LocalTime duration;
	@OneToOne
	private Project project;

	public Intervention() {
		super();
	}

	public Intervention(String description) {
		this.description = description;
	}

	public Intervention(String description, String commentaire, Person owner, LocalDateTime createdDate,
			LocalDateTime lastModifiedDate) {
		super();
		this.description = description;
		this.commentaire = commentaire;
		this.owner = owner;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public void setSimpleValue(String value) {
		this.setDescription(value);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		Intervention other = (Intervention) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Intervention n°" + this.getId();
	}

//	@Override
//	public ValueProvider<?, ?> getPropertyValue(ValueProvider provider) {
//		// TODO Auto-generated method stub
//		
//		Object obj = provider.apply(this);
//		
//		ValueProvider<Displayable, String> newprovider = new ValueProvider<Displayable, String>() {
//
//			@Override
//			public String apply(Displayable source) {
//				// TODO Auto-generated method stub
//				return this.apply(source);
//			}
//		};
//		
//		return null;
//	}

//	@Override
//	public ValueProvider<? extends Serializable , ?> getPropertyValue(ValueProvider<? ,?> provider) {
////		provider.apply(this);
//		return null;
//	}

}
