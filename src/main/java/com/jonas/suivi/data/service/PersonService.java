package com.jonas.suivi.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import com.jonas.suivi.data.entity.Person;

import java.time.LocalDate;

@Service
public class PersonService extends CrudService<Person, Integer> {

    private PersonRepository repository;

    public PersonService(@Autowired PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PersonRepository getRepository() {
        return repository;
    }

}
