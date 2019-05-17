package com.ws.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ws.spring.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long>{
	
	// https://www.baeldung.com/spring-data-jpa-query
		// @Query("SELECT u FROM User u WHERE u.nameUser = ?1")
		@Query("SELECT u FROM UserDetails u WHERE u.userName = :userName")
		public UserDetails findUserDetailsByUserName(@Param("userName") String userName);
		
		@Query("SELECT u FROM UserDetails u WHERE u.userName = :emailId")
		public UserDetails findUserDetailsByEmailId(@Param("emailId") String emailId);
}

