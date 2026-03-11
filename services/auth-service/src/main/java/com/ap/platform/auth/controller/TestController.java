package com.ap.platform.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/test")
	public String test(@RequestHeader(value="X-User") String user) {
		return "Request from authenticated user: "+user;
	}
}
