package com.example.codelabsvc.controller.response.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDTO {
    private String id;
    private String username;
    private String createdAt;
    private String text;
    private String code;

    public CommentResponseDTO(String id, String username, String createdAt, String text, String code) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.text = text;
        this.code = code;
    }
}
