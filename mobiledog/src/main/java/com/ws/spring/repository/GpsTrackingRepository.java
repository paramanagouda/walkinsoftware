package com.ws.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ws.spring.model.GpsTrackingDetails;

public interface GpsTrackingRepository extends JpaRepository<GpsTrackingDetails, Long> {

	@Query("SELECT g FROM GpsTrackingDetails g WHERE g.mobileNumber = :mobileNumber")
	List<GpsTrackingDetails> queryUserGpsTrackingDetails(@Param("mobileNumber") String mobileNumber);

}
