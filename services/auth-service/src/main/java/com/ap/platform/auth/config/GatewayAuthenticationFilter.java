package com.ap.platform.auth.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayAuthenticationFilter extends OncePerRequestFilter{

	private static final String GATEWAY_SECRET = "gateway-secret-key";

	protected void doFilterInternal(HttpServletRequest request,
								HttpServletResponse response,
								FilterChain filterChain)
			throws ServletException, IOException{
		
		String path = request.getRequestURI();
		
		//allow auth endpoints without gateway verification
		if(path.startsWith("/api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
			
		String gatewaySecret = request.getHeader("X-Gateway-Secret");
		if(gatewaySecret==null || !gatewaySecret.equals(GATEWAY_SECRET)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		filterChain.doFilter(request, response);
	}
}
