package com.jonas.suivi.backend.model.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.jonas.suivi.backend.model.Displayable;

@Entity
public class Person  implements Serializable, Displayable {

	@Id @GeneratedValue
	Long id;
	private String login;
	private String firstName;
	private String lastName;
	public String email;
	
	public Person() {
		super();
	}

	public Person(String name) {
		super();
		this.firstName = name;
	}

	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return new StringBuilder(firstName).append(" ").append(lastName).toString();
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Person other = (Person) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String getLabel() {
		return firstName.concat(" ").concat(lastName);
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	

	
	
}
