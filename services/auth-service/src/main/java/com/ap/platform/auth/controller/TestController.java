package com.ap.platform.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/test")
	public String test(@RequestHeader(value="X-User", required = false) String username,
			@RequestHeader(value = "X-Gateway-Auth", required = false) String gatewayAuth) {
		
		if(!"true".equals(gatewayAuth)) {
			throw new RuntimeException("Unauthorized direct access");
		}
		
		return "Request from authenticated user: "+username;
	}
}
