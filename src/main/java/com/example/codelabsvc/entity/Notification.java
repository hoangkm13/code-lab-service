package com.example.codelabsvc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document(collection = "notification")
public class Notification extends EntityBase {
    private String id;
    private String userId;
    private String templateId;
    private String privateContent;
    private Boolean isSeen;

    public Notification(String updatedAt, String createdBy, String updateBy, String userId, String templateId, String privateContent) {
        super(LocalDateTime.now().toString(), updatedAt, createdBy, updateBy);
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.templateId = templateId;
        this.privateContent = privateContent;
        this.isSeen = false;
    }
}
