package com.ws.spring.rest.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.GpsTrackingDetails;
import com.ws.spring.model.SimRemovalDetails;
import com.ws.spring.service.GpsTrackingService;
import com.ws.spring.service.SimRemovalDetailsService;
import com.ws.spring.util.ClientResponseUtil;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/gpstracking")
@Api(value="GPS Management System", description="Operations pertaining to gps in Global Position System")
public class GlobalPositionController {

	Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	SimRemovalDetailsService simRemovalDetailsService;

	@Autowired
	GpsTrackingService gpsTrackingService;

	@PostMapping("/v1/simremoval")
	public ClientResponseBean simRemoval(@RequestBody SimRemovalDetails simRemovalDetails) {
		SimRemovalDetails removalDetails = simRemovalDetailsService.insert(simRemovalDetails);
		if (null != removalDetails) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@GetMapping("/v1/querySimRemovalDetails")
	public List<SimRemovalDetails> querySimRemovalDetails(@RequestParam String mobileNumber) {
		try {
			return simRemovalDetailsService.querySimRemovalDetailsByMobileNumber(mobileNumber);
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Registration got an Error", ex);
		}
	}

	@PostMapping("/v1/addUserGpsTrackingDetails")
	public ClientResponseBean addUserGpsTrackingDetails(@RequestBody GpsTrackingDetails userGpsTrackingDetails) {
		gpsTrackingService.addUserGpsTrackingDetails(userGpsTrackingDetails);
		return null;
	}

	@GetMapping("/v1/queryUserGpsTrackingDetails")
	public List<GpsTrackingDetails> queryUserGpsTrackingDetails(@RequestParam String mobileNumber) {
		try {
			return gpsTrackingService.queryUserGpsTrackingDetails(mobileNumber);
		} catch (Exception ex) {
			logger.error("Exception Occure : {} ", ex.getMessage(), ex);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Registration got an Error", ex);
		}
	}

	@PostMapping("/v1/emeregency")
	public ClientResponseBean emeregency(@RequestBody SimRemovalDetails simRemovalDetails) {
		SimRemovalDetails removalDetails = simRemovalDetailsService.insert(simRemovalDetails);
		if (null != removalDetails) {
			// Send SMS to Secondary mobile number
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}
}
