package com.jonas.suivi.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.data.entity.Person;

import java.time.LocalDate;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}