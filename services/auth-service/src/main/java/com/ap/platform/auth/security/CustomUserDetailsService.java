package com.ap.platform.auth.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		
		return new org.springframework.security.core.userdetails.User
				(user.getUsername(),
				 user.getPassword(),
				 user.getRoles()
						.stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
						.collect(Collectors.toList()));
	}
	
}
