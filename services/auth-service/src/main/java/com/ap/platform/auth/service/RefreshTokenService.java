package com.ap.platform.auth.service;

import java.util.Optional;

import com.ap.platform.auth.entity.RefreshToken;

public interface RefreshTokenService {

	RefreshToken createRefreshToken(Long userId);
	
	Optional<RefreshToken> findByToken(String token);
	
	RefreshToken verifyExpiration(RefreshToken token);
	
	void deleteByUserId(Long userId);
}
