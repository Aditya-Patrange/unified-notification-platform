package com.ap.platform.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ap.platform.auth.entity.RefreshToken;
import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.repository.RefreshTokenRepository;
import com.ap.platform.auth.repository.UserRepository;
import com.ap.platform.auth.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Value("${jwt.refresh.expiration}")
	private Long refreshTokenDuration;

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(user);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDuration));
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException("Refresh token expired. Please login again.");
		}
		return token;
	}

	/**
	 * Used during:
	 * Logout, Password change, account suspension
	 */
	@Override
	public void deleteByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		refreshTokenRepository.deleteByUser(user);
	}

}
