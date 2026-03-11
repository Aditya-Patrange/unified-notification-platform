package com.ap.platform.gateway.security;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ap.platform.security.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered{
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
	    this.jwtUtil = jwtUtil;
	}

	    @Override
	    public Mono<Void> filter(ServerWebExchange exchange,
	                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

	        String path = exchange.getRequest().getURI().getPath();

	        // Allow auth endpoints without token
	        if (path.startsWith("/api/auth")) {
	            return chain.filter(exchange);
	        }

	        String authHeader = exchange.getRequest()
	                .getHeaders()
	                .getFirst(HttpHeaders.AUTHORIZATION);

	        System.out.println("Auth header: " + authHeader);
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	            return exchange.getResponse().setComplete();
	        }

	        String token = authHeader.substring(7);
	        
	        System.out.println("Token received in gateway: " + token);
	        boolean valid = jwtUtil.validateToken(token);

	        System.out.println("Token valid: " + valid);

	        if (!valid) {
	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	            return exchange.getResponse().setComplete();
	        }

	        String username = jwtUtil.extractUsername(token);
	        ServerWebExchange mutatedExchange = exchange.mutate()
						        						.request(exchange.getRequest()
						        						.mutate()
						        						.header("X-User", username)
						        						.header("X-Gateway-Auth", "true")
						        						.build())
						        					.build();
	        
	        return chain.filter(mutatedExchange);
	    }

	    @Override
	    public int getOrder() {
	        return -1;
	    }
}
