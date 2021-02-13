package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.impl.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
