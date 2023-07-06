package com.example.codelabsvc.controller.response.comment;

import lombok.Data;

@Data
public class SaveNotificationRequestDTO {
    private String templateId;
    private String privateContent;
    private String userId;

}
