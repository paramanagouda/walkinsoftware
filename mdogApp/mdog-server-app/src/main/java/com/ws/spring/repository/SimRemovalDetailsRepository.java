package com.ws.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ws.spring.model.SimRemovalDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SimRemovalDetailsRepository extends JpaRepository<SimRemovalDetails, Long> {

    @Query("Select s from SimRemovalDetails s where s.mobileNumber = :mobileNumber")
    SimRemovalDetails querySimRemovalDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
