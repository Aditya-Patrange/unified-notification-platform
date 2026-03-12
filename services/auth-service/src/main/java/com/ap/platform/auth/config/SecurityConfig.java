package com.ap.platform.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ap.platform.auth.security.ServiceJwtValidationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final GatewayAuthenticationFilter gatewayAuthenticationFilter;
	private final ServiceJwtValidationFilter serviceJwtValidationFilter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
			GatewayAuthenticationFilter gatewayAuthenticationFilter,
			ServiceJwtValidationFilter serviceJwtValidationFilter)
	{
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.gatewayAuthenticationFilter = gatewayAuthenticationFilter;
		this.serviceJwtValidationFilter = serviceJwtValidationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.httpBasic(httpBasic -> httpBasic.disable()).formLogin(form -> form.disable())
				//1) verify request came from gateway
				.addFilterBefore(gatewayAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				//2) verify jwt signature
				.addFilterAfter(serviceJwtValidationFilter, GatewayAuthenticationFilter.class)
				//3) populate spring security context
				.addFilterAfter(jwtAuthenticationFilter, ServiceJwtValidationFilter.class);

		return http.build();
	}
}
