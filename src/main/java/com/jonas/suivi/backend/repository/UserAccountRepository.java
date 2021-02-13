package com.jonas.suivi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jonas.suivi.backend.model.impl.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	@Query(value=" select u from UserAccount u where u.login = :login")
	public UserAccount findByLogin(@Param("login")String login);
	
}
