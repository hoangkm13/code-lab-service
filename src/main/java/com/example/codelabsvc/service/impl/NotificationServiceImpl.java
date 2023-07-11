package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.response.comment.SaveNotificationRequestDTO;
import com.example.codelabsvc.controller.response.notification.NotificationResponse;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.entity.NotificationTemplate;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.NotificationRepository;
import com.example.codelabsvc.repository.NotificationTemplateRepository;
import com.example.codelabsvc.service.NotificationService;
import com.example.codelabsvc.util.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationTemplateRepository notificationTemplateRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationTemplateRepository notificationTemplateRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationTemplateRepository = notificationTemplateRepository;
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
    public Page<NotificationResponse> getAllNotification(int page, int size) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        List<Notification> notificationList = notificationRepository.getAllByUserId(authentication.getId());

        notificationList.forEach(n -> {
            NotificationTemplate notificationTemplate = null;
            if (n.getTemplateId() != null) {
                notificationTemplate = notificationTemplateRepository.findById(n.getTemplateId()).orElse(null);
            }
            notificationResponses.add(new NotificationResponse(n, notificationTemplate));
        });

        return new PageImpl<>(ListUtils.getPage(new ArrayList<>(notificationResponses), page, size));
    }

    @Override
    public Notification deleteNotification(String notificationId) throws CustomException {
        Notification notification = notificationRepository.findNotificationById(notificationId);

        if (notification == null) {
            throw new CustomException(ErrorCode.NOTIFICATION_NOT_EXIST);
        }

        notificationRepository.delete(notification);

        return notification;
    }
}
