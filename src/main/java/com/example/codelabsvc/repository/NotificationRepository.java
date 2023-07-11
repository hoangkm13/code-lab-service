package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Notification findNotificationById(String notificationId);
    Page<Notification> getAllNotificationByUserId(String userId, Pageable pageable);
}
