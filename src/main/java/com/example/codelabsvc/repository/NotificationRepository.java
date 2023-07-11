package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Notification findNotificationById(String notificationId);
    List<Notification> getAllByUserId(String userId);
}
