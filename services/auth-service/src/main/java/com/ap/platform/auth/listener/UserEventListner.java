package com.ap.platform.auth.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ap.platform.auth.events.UserRegisteredEvent;

@Component
public class UserEventListner {

	@EventListener
	public void handleUserRegisteredEvent(UserRegisteredEvent event) {
		System.out.println("EVENT RECEIVED in Auth Service");
		System.out.println("User registered: "+ event.getUsername());
		System.out.println("Email: "+ event.getEmail());
		System.out.println("Time: "+ event.getTimestamp());
	}
}
