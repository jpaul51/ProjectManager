package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonas.suivi.backend.model.impl.Intervention;

public interface InterventionRepository extends JpaRepository<Intervention, Long>{

	public default void saveOne(Intervention d) {
		save(d);
	}

}
