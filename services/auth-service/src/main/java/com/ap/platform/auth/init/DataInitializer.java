package com.ap.platform.auth.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ap.platform.auth.entity.Role;
import com.ap.platform.auth.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public DataInitializer(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) {
		seedRole("USER");
		seedRole("ADMIN");

	}
	
	 private void seedRole(String roleName) {
	        if (roleRepository.findByName(roleName).isEmpty()) {
	            Role role = new Role();
	            role.setName(roleName);
	            roleRepository.save(role);
	        }
	    }

}
