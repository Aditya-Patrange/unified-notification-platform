package com.ap.platform.notification.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ap.platform.events.UserRegisteredEvent;
import com.ap.platform.notification.entity.Notification;
import com.ap.platform.notification.model.NotificationStatus;
import com.ap.platform.notification.repository.NotificationRepository;

@Service
public class UserEventConsumer {

	private final NotificationRepository notificationRepository;
	
	public UserEventConsumer(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	@KafkaListener(topics = "user-events", groupId = "notification-group")
	public void consume(UserRegisteredEvent event) {
		System.out.println("Received from kafka: "+ event.getUsername());
		
		Notification notification = new Notification();
		notification.setUsername(event.getUsername());
		notification.setTitle("Welcome");
		notification.setMessage("Hello "+ event.getUsername() +", your account was created successfully.");
		notification.setStatus(NotificationStatus.UNREAD);
		
		notificationRepository.save(notification);
	}
	
}
