package com.ws.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ws.spring.model.Role;
import com.ws.spring.repository.RoleRepository;

@Configuration
public class AppCommandLineRunner implements CommandLineRunner{

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role superAdminRole = new Role(1L,"SuperAdmin");
		roleRepository.save(superAdminRole);		
		Role adminRole = new Role(2L,"Admin");
		roleRepository.save(adminRole);		
		Role reportRole = new Role(3L,"Reporter");
		roleRepository.save(reportRole);		
		Role userRole = new Role(4L,"User");
		roleRepository.save(userRole);		
	}

}
