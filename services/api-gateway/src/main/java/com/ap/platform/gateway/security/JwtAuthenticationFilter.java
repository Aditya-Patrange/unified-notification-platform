package com.ap.platform.gateway.security;

import java.util.List;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ap.platform.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
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

	  log.info("Auth header: " + authHeader);
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	            return exchange.getResponse().setComplete();
	        }

	        String token = authHeader.substring(7);
	        
	        log.info("Token received in gateway: " + token);
	        boolean valid = jwtUtil.validateToken(token);

	        log.info("Token valid: " + valid);

	        if (!valid) {
	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	            return exchange.getResponse().setComplete();
	        }

	        String username = jwtUtil.extractUsername(token);
	        
	        List<String> roles = jwtUtil.extractRoles(token);
	        String roleHeader = String.join(",",roles);
	        
	        ServerWebExchange mutatedExchange = exchange.mutate()
						        						.request(exchange.getRequest()
						        						.mutate()
						        						.header("X-User", username)
						        						.header("X-Roles", roleHeader)
						        						.header("X-Gateway-Auth", "true")
						        						.header("X-Gateway-Secret", "gateway-secret-key")
						        						.header("X-Auth-Token", token)
						        						.build())
						        					.build();
	        
	        return chain.filter(mutatedExchange);
	    }

	    @Override
	    public int getOrder() {
	        return -1;
	    }
}
