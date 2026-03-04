package com.ap.platform.auth.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ap.platform.auth.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenCleanupService {

	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshTokenCleanupService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}
	
	@Scheduled(cron = "0 0 * * * *")
	@Transactional
	public void cleanExpiredTokens() {
		refreshTokenRepository.deleteExpiredTokens();
		System.out.println("Expired refresh tokens cleaned up.");
	}
}
