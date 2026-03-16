package com.ap.platform.notification.dto;

import jakarta.validation.constraints.NotBlank;

public class NotificationRequestDto {
	@NotBlank
	private String title;
	@NotBlank
	private String message;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
