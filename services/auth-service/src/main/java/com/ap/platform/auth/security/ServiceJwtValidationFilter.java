package com.ap.platform.auth.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ap.platform.security.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ServiceJwtValidationFilter extends OncePerRequestFilter{

	private final JwtUtil jwtUtil;
	
	public ServiceJwtValidationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
				HttpServletResponse response,
				FilterChain filterChain)
	throws ServletException,IOException{
		
		String path = request.getRequestURI();
		
		if(path.startsWith("/api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
}
