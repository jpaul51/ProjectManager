package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.impl.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
