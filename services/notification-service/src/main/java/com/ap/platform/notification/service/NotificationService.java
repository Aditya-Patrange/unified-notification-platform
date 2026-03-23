package com.ap.platform.notification.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ap.platform.notification.entity.Notification;
import com.ap.platform.notification.model.NotificationStatus;
import com.ap.platform.notification.repository.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	
	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	public Page<Notification> getUserNotifications(String username, Pageable pageable){
		
			LocalDateTime expiTime = LocalDateTime.now().minusDays(2);
			//return notificationRepository.findByUsernameOrderByCreatedAtDesc(username, expiTime, pageable);
			return notificationRepository.findActiveNotifications(username, expiTime, pageable);
	}

	@Transactional // Ensures atomic update and enables JPA dirty checking
	public void markAsRead(Long id) {
		// Fetch notification by ID or throw exception if not found
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Notification not found"));
		 // Update status to READ
		notification.setStatus(NotificationStatus.READ);
		// Set the timestamp when notification was read (used later for expiry logic)
		notification.setReadAt(LocalDateTime.now());
	
		// No explicit save() required
	    // JPA will automatically persist changes due to dirty checking
	}
}
