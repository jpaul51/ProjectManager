package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.Name;

@Entity
public class Project   implements Serializable, Displayable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4807833043624930166L;
	
	@Id @GeneratedValue
	Long id;
	@Name("name")
	private String name;
	@Name("description")
	private String description;
	
	@ManyToOne
	private Person projectManager;
	
	public Project() {
		
	}
	
	public Project(String projectName) {
		this.name = projectName;
	}
	
	
	
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public Person getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Person projectManager) {
		this.projectManager = projectManager;
	}

	

	@Override
	public String getLabel() {
		return this.getName();
	}


	@Override
	public String toString() {
		return name;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((projectManager == null) ? 0 : projectManager.hashCode());
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
		Project other = (Project) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (projectManager == null) {
			if (other.projectManager != null)
				return false;
		} else if (!projectManager.equals(other.projectManager))
			return false;
		return true;
	}




	
	
	
	
}
