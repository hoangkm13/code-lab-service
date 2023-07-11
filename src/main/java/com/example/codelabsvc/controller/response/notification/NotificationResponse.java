package com.example.codelabsvc.controller.response.notification;

import com.example.codelabsvc.entity.Notification;
import com.example.codelabsvc.entity.NotificationTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponse {
    private Notification notification;
    private NotificationTemplate notificationTemplate;
}
