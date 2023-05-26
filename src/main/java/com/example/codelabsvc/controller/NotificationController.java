package com.example.codelabsvc.controller;

import com.example.codelabsvc.dto.SaveNotificationRequestDTO;
import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.service.NotificationService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public Notification saveNotification(@RequestBody SaveNotificationRequestDTO dto) throws CustomException {
        return notificationService.saveNotification(dto);
    }

    @PostMapping("{notificationId}")
    public Notification updateNotification(@PathVariable String notificationId) {
        return notificationService.updateNotification(notificationId);
    }

    @GetMapping("{userId}")
    public Page<Notification> getAllNotification(@PathVariable String userId,
                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "5") int size) {
        return notificationService.getAllNotification(userId, page, size);
    }
}
