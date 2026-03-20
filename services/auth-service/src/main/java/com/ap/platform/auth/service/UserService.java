package com.ap.platform.auth.service;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ap.platform.auth.dto.LoginRequestDto;
import com.ap.platform.auth.dto.RefreshTokenResponseDto;
import com.ap.platform.auth.dto.RegisterRequestDto;
import com.ap.platform.auth.dto.UserResponseDto;
import com.ap.platform.auth.entity.RefreshToken;
import com.ap.platform.auth.entity.Role;
import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.events.UserEventProducer;
import com.ap.platform.auth.repository.RoleRepository;
import com.ap.platform.auth.repository.UserRepository;
import com.ap.platform.events.UserRegisteredEvent;
import com.ap.platform.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final RefreshTokenService refreshTokenService;
	private final ApplicationEventPublisher eventPublisher;
	private final UserEventProducer userEventProducer;
	
	public UserService(UserRepository userRepository,RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, JwtUtil jwtUtil,RefreshTokenService refreshTokenService,
			ApplicationEventPublisher eventPublisher,UserEventProducer userEventProducer) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.refreshTokenService = refreshTokenService;
		this.eventPublisher = eventPublisher;
		this.userEventProducer = userEventProducer;
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
		
		//for notification (publish event) spring event
		//eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getUsername(), savedUser.getEmail()));
		//replaced with kafka
		userEventProducer.sendUserRegisteredEvent(new UserRegisteredEvent(savedUser.getUsername(), savedUser.getEmail()));
		
		//change this for notification
		return new UserResponseDto(savedUser.getId(), savedUser.getUsername() , savedUser.getEmail());
		
		
	}
	
	
	public RefreshTokenResponseDto login(LoginRequestDto request) {
		User user = userRepository.findByUsername(request.getUsername())
					.orElseThrow(() -> new RuntimeException("Invalid username or password"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}
		List<String> roles = user.getRoles()
								.stream()
								.map(role -> role.getName())
								.toList();	
		
		String accessToken =  jwtUtil.generateToken(user.getUsername(),roles);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
		
		return new RefreshTokenResponseDto(accessToken, refreshToken.getToken());
	}
	
}
