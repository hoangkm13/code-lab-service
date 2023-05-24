package com.example.codelabsvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDTO {
    private String message;
    private String sender;
    private String receiver;
}
