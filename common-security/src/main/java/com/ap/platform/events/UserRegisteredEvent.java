package com.ap.platform.events;

import java.time.LocalDateTime;

public class UserRegisteredEvent {

	private String username;
	private String email;
	private LocalDateTime timestamp;
	private String eventId;
	
	public UserRegisteredEvent() {
	}

	public UserRegisteredEvent(String username,String email, String eventId) {
		this.username = username;
		this.email = email;
		this.timestamp = LocalDateTime.now();
		this.eventId = eventId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
		this.username=username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp){
		this.timestamp = timestamp;
	}
	public String getEventId(){
		return eventId;
	}
	public void setEventId(String eventId){
		this.evenId = eventId;	
	}


}
