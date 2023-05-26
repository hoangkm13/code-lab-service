package com.example.codelabsvc.dto;

import lombok.Data;

@Data
public class SaveNotificationRequestDTO {
    private String templateId;
    private String privateContent;
    private String userId;

}
