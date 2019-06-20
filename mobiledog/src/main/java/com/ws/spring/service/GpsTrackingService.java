package com.ws.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.spring.model.GpsTrackingDetails;
import com.ws.spring.repository.GpsTrackingRepository;

@Service
public class GpsTrackingService {

	@Autowired
	GpsTrackingRepository gpsTrackingRepository;

	public void addUserGpsTrackingDetails(GpsTrackingDetails userGpsTrackingDetails) {
		gpsTrackingRepository.save(userGpsTrackingDetails);
	}
	
	public List<GpsTrackingDetails> queryUserGpsTrackingDetails(String mobileNumber)
	{
		return gpsTrackingRepository.queryUserGpsTrackingDetails(mobileNumber);
	}

}
