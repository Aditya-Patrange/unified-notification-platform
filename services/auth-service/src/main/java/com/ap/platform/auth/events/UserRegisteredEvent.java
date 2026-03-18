package com.ap.platform.auth.events;

import java.time.LocalDateTime;

public class UserRegisteredEvent {

	private String username;
	private String email;
	private LocalDateTime timestamp;
	
	public UserRegisteredEvent(String username,String email, String timestamp) {
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
