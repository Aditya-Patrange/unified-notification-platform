package com.ap.platform.notification.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ap.platform.events.UserRegisteredEvent;
import com.ap.platform.notification.entity.Notification;
import com.ap.platform.notification.model.NotificationStatus;
import com.ap.platform.notification.repository.NotificationRepository;

@Component
public class UserEventListner {

	private final NotificationRepository notificationRepository;
	
	public UserEventListner(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	@EventListener
	public void handleUserRegisteredEvent(UserRegisteredEvent event) {
		System.out.println("EVENT RECEIVED in Notification Service");
		Notification notification = new Notification();
		notification.setUsername(event.getUsername());
		notification.setTitle("Welcome");
		notification.setMessage("Hello "+ event.getUsername() +", your account was created successfully.");
		notification.setStatus(NotificationStatus.UNREAD);
		
		notificationRepository.save(notification);
	}
}
