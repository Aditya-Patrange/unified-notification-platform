package com.ap.platform.notification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ap.platform.notification.entity.Notification;
import com.ap.platform.notification.repository.NotificationRepository;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	
	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	public Page<Notification> getUserNotifications(String username, Pageable pageable){
			return notificationRepository.findByUsernameOrderByCreatedAtDesc(username, pageable);
	}
}
