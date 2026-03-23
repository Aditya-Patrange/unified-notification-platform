package com.ap.platform.notification.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ap.platform.notification.model.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String message;
	
	@Enumerated(EnumType.STRING)
	private NotificationStatus status = NotificationStatus.UNREAD;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "read_at")
	private LocalDateTime readAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
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
	
	public NotificationStatus getStatus() {
		return status;
	}
	
	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	
	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}
	public LocalDateTime getReadAt() {
		return readAt;
	}
}
