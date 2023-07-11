package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.response.comment.SaveNotificationRequestDTO;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;

public interface NotificationService {
    Notification saveNotification(SaveNotificationRequestDTO dto) throws CustomException;
    Notification updateNotification(String notificationId);
    Page<Notification>  getAllNotification(String userId, int page, int size);

    Notification deleteNotification(String notificationId) throws CustomException;
}
