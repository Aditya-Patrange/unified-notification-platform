package com.ap.platform.auth.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ap.platform.auth.dto.RegisterRequestDto;
import com.ap.platform.auth.dto.UserResponseDto;
import com.ap.platform.auth.entity.Role;
import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.repository.RoleRepository;
import com.ap.platform.auth.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public UserResponseDto registerUser(RegisterRequestDto request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username already exist");
		}
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exist");
		}
		
		Role userRole = roleRepository.findByName("USER").
								orElseThrow(()-> new RuntimeException("Default role not found"));
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(Collections.singleton(userRole));
		
		User savedUser =  userRepository.save(user);
		
		return new UserResponseDto(savedUser.getId(), savedUser.getUsername() , savedUser.getEmail());
	}
	
}
