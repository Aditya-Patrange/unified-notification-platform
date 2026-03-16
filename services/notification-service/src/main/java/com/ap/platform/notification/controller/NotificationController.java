package com.ap.platform.notification.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.platform.notification.entity.Notification;
import com.ap.platform.notification.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@GetMapping
	public Page<Notification> getNotifications(
				@RequestParam(name="page",defaultValue = "0") int page,
				@RequestParam(name="size",defaultValue = "10") int size,
				HttpServletRequest request
			)
	{
		String username = request.getHeader("X-User");
		PageRequest pageable = PageRequest.of(page, Math.min(size, 50));
		
		return notificationService.getUserNotifications(username, pageable);
	}
	
}
