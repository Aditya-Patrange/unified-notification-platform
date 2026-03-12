package com.ap.platform.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ap.platform.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JwtConfig {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	@Bean
	public JwtUtil jwtUtil() {
		log.info("Gateway JWT secret: " + secret);
		return new JwtUtil(secret,expiration);
	}
}
