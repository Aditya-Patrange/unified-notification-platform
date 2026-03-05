package com.ap.platform.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ap.platform.auth.entity.RefreshToken;
import com.ap.platform.auth.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	Optional<RefreshToken> findByToken(String token);
	
	void deleteByUser(User user);
	
	@Modifying
	@Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < CURRENT_TIMESTAMP")
	void deleteExpiredTokens();
}
