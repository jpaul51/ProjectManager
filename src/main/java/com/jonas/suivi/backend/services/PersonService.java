package com.jonas.suivi.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Person;
import com.jonas.suivi.backend.repository.PersonRepository;

@Service("Person")
public class PersonService extends CrudService<Person, Long> implements DisplayableService{

	
	@Autowired
	private PersonRepository personRepo;
	
	
	
	
	public void addPerson(Person p) {
		
		personRepo.save(p);
	}

	@Override
	protected JpaRepository<Person, Long> getRepository() {
		return personRepo;
	}


	@Override
	public <T extends Displayable> void update(T d) {
		personRepo.save((Person)d);
	}

	@Override
	public <T extends Displayable> List<T> getAll() {
		return (List<T>) personRepo.findAll();
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		personRepo.delete((Person) d);		
	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		// TODO Auto-generated method stub
		return (JpaRepository<T, Long>) personRepo;
	}
	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		// TODO Auto-generated method stub
		
		Person p = new Person();
		p.setLogin(d);
		
		Example<Person> personExample = (Example<Person>) Example.of(p);

		return (Optional<T>) personRepo.findOne((Example<Person>) personExample);
	}
	
}
