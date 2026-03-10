package com.ap.platform.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.platform.auth.dto.LoginRequestDto;
import com.ap.platform.auth.dto.RefreshTokenRequestDto;
import com.ap.platform.auth.dto.RefreshTokenResponseDto;
import com.ap.platform.auth.dto.RegisterRequestDto;
import com.ap.platform.auth.dto.UserResponseDto;
import com.ap.platform.auth.entity.RefreshToken;
import com.ap.platform.auth.entity.User;
import com.ap.platform.auth.repository.RefreshTokenRepository;
import com.ap.platform.security.JwtUtil;
import com.ap.platform.auth.service.RefreshTokenService;
import com.ap.platform.auth.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtUtil jwtUtil;
	public AuthController(UserService userService,RefreshTokenService refreshTokenService,
			RefreshTokenRepository refreshTokenRepository,JwtUtil jwtUtil) {
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public UserResponseDto registerUser(@Valid @RequestBody RegisterRequestDto request) {
		return userService.registerUser(request);
	}

	@PostMapping("/login")
	public RefreshTokenResponseDto login(@Valid @RequestBody LoginRequestDto request) {
		return userService.login(request);
	}

	@PostMapping("/refresh")
	public RefreshTokenResponseDto refreshToken(@RequestBody @Valid RefreshTokenRequestDto request) {
		String requestToken = request.getRefreshToken();
		
		RefreshToken refreshToken= refreshTokenService.findByToken(requestToken)
				.map(refreshTokenService::verifyExpiration)
				.orElseThrow(()-> new RuntimeException("Refresh token not found"));
		
		User user = refreshToken.getUser();
		
		//delete old refresh tokrn (rotation)
		refreshTokenRepository.delete(refreshToken);
		
		//create new refresh token
		RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
		
		//generate new access token 
		String newAcessToken = jwtUtil.generateToken(user.getUsername());
		
		return new RefreshTokenResponseDto(newAcessToken, newRefreshToken.getToken());
		
	}
}
