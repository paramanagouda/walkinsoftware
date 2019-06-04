package com.ws.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws.spring.exception.ClientResponseBean;
import com.ws.spring.model.GpsTrackingDetails;
import com.ws.spring.model.SimRemovalDetails;
import com.ws.spring.service.SimRemovalDetailsService;
import com.ws.spring.util.ClientResponseUtil;

@RestController
@RequestMapping(value = "/gpstracking/v1")
public class GlobalPositionController {

	@Autowired
	SimRemovalDetailsService simRemovalDetailsService;

	@PostMapping("/simremoval")
	public ClientResponseBean simRemoval(@RequestBody SimRemovalDetails simRemovalDetails) {
		SimRemovalDetails removalDetails = simRemovalDetailsService.insert(simRemovalDetails);
		if (null != removalDetails) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@PostMapping("/querySimRemovalDetails")
	public ClientResponseBean querySimRemovalDetails(@RequestParam String mobileNumber) {
		SimRemovalDetails removalDetails = simRemovalDetailsService.querySimRemovalDetailsByMobileNumber(mobileNumber);
		if (null != removalDetails) {
			return ClientResponseUtil.getSuccessResponse();
		}
		return ClientResponseUtil.getErrorResponse();
	}

	@PostMapping("/addUserGpsTrackingDetails")
	public ClientResponseBean addUserGpsTrackingDetails(@RequestBody GpsTrackingDetails userGpsTrackingDetails) {
		// UserGpsTrackingDetails gpsTrackingDetails = Si

		return null;
	}
}
