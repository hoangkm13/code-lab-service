package com.example.codelabsvc.entity;

import lombok.Data;


import org.springframework.data.mongodb.core.mapping.Document;




import java.time.LocalDateTime;

@Data
@Document(collection = "chat")
public class Chat {

    private String id;
    private String message;
    private String sender;
    private String receiver;
    private Integer chatId;
    private LocalDateTime createdAt;
}
