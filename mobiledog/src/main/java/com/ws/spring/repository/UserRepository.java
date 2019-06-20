package com.ws.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ws.spring.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

	// https://www.baeldung.com/spring-data-jpa-query
	// @Query("SELECT u FROM User u WHERE u.nameUser = ?1")
	@Query("SELECT u FROM UserDetails u WHERE u.userName = :userName")
	UserDetails findUserDetailsByUserName(@Param("userName") String userName);

	@Query("SELECT u FROM UserDetails u WHERE u.userName = :emailId")
	UserDetails findUserDetailsByEmailId(@Param("emailId") String emailId);

	@Query("SELECT u FROM UserDetails u WHERE u.userName = :userName or u.emailId = :userName or u.mobileNumber = :userName")
	UserDetails queryLoginUserDetails(@Param("userName") String userName);

	@Query("SELECT u FROM UserDetails u WHERE u.isActive = 0")
	List<UserDetails> queryInactiveUsers();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE UserDetails u set u.approveStatus = :approveStatus, u.isActive = :isActive, u.reason = :reason where u.id in :userIds")
	void updateUserActivation(@Param("userIds") Set<Long> userIds, @Param("approveStatus") int approveStatus, @Param("isActive") int isActive,
			@Param("reason") String reason);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE UserDetails u set u.mpin = :mpin where u.mobileNumber = :mobileNumber")
	void updateMpin(@Param("mobileNumber") String mobileNumber, @Param("mpin") String mpin);
	
	@Query("SELECT u FROM UserDetails u WHERE u.userName LIKE :userName or u.fullName LIKE :userName and u.mobileNumber LIKE :mobileNumber")
	List<UserDetails> queryUserDetailsByUserNameOrMobile(@Param("userName") String userName,@Param("mobileNumber") String mobileNumber);

}
