package com.ws.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.spring.model.SimRemovalDetails;
import com.ws.spring.repository.SimRemovalDetailsRepository;

@Service
public class SimRemovalDetailsService {

    @Autowired
    SimRemovalDetailsRepository removalDetailsRepository;

    public SimRemovalDetails insert(SimRemovalDetails simRemovalDetails) {
        return removalDetailsRepository.save(simRemovalDetails);
    }

    public SimRemovalDetails findById(Long id) {
        Optional<SimRemovalDetails> options = removalDetailsRepository.findById(id);
        return options.get();
    }

    public List<SimRemovalDetails> querySimRemovalDetailsByMobileNumber(String mobileNumber) {
        return removalDetailsRepository.querySimRemovalDetailsByMobileNumber(mobileNumber);
    }
}
