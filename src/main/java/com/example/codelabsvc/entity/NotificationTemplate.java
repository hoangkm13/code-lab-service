package com.example.codelabsvc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "notificationTemplates")
public class NotificationTemplate {
    private String id;
    private String content;
}
