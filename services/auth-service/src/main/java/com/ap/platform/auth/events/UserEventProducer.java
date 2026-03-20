package com.ap.platform.auth.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ap.platform.events.UserRegisteredEvent;

@Service
public class UserEventProducer {

	private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplete;
	
	public UserEventProducer(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
		this.kafkaTemplete = kafkaTemplate;
	}
	
	public void sendUserRegisteredEvent(UserRegisteredEvent event) {
		kafkaTemplete.send("user-events",event);
		System.out.println("Sent to kafka"+ event.getUsername());
	}
}
