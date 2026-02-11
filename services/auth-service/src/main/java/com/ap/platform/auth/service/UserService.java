package com.ap.platform.auth.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.ap.platform.auth.entity.Role;
import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.repository.RoleRepository;
import com.ap.platform.auth.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	public UserService(UserRepository userRepository,RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Transactional
	public User registerUser(String username, String email, String password) {
		if(userRepository.existByUsername(username)) {
			throw new RuntimeException("Username already exist");
		}
		if(userRepository.existByEmail(email)) {
			throw new RuntimeException("Email already exist");
		}
		
		Role userRole = roleRepository.findByName("USER").
								orElseThrow(()-> new RuntimeException("Default role not found"));
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);//hash it later
		user.setRoles(Collections.singleton(userRole));
		
		return userRepository.save(user);
	}
	
}
