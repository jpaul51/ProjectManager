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




	
	
	
	
}
