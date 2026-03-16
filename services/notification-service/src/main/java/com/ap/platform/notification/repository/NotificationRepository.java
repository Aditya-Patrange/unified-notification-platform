package com.ap.platform.notification.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.platform.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

	List<Notification> findByUsername(String username);
	
	Page<Notification> findByUsernameOrderByCreatedAtDesc(String username,Pageable pageable);
}
