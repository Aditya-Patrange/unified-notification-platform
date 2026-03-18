package com.ap.platform.auth.events;

import java.time.LocalDateTime;

public class UserRegisteredEvent {

	private final String username;
	private final String email;
	private final LocalDateTime timestamp;
	
	public UserRegisteredEvent(String username,String email) {
		this.username = username;
		this.email = email;
		this.timestamp = LocalDateTime.now();
	}
	
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
}
