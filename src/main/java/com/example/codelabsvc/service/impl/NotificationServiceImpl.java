package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.dto.SaveNotificationRequestDTO;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.repository.NotificationRepository;
import com.example.codelabsvc.service.NotificationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification saveNotification(SaveNotificationRequestDTO dto) {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Notification notification = new Notification(
                "",
                authentication.getUsername(),
                authentication.getUsername(),
                dto.getUserId(),
                dto.getTemplateId(),
                dto.getPrivateContent());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(String notificationId) {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        notification.setIsSeen(true);
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> getAllNotification(String userId,int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.getAllNotification(userId,pageable);
    }


}
