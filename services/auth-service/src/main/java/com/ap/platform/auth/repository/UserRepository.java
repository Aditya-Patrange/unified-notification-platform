package com.ap.platform.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.platform.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	boolean existByUsername(String username);
	
	boolean existByEmail(String email);
	
}
