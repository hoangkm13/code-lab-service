package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.response.comment.SaveNotificationRequestDTO;
import com.example.codelabsvc.controller.response.notification.NotificationResponse;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {
    Notification saveNotification(SaveNotificationRequestDTO dto) throws CustomException;
    Notification updateNotification(String notificationId);
    Page<NotificationResponse> getAllNotification(int page, int size);

    List<Notification> markAsReadAll();

    Notification deleteNotification(String notificationId) throws CustomException;
}
