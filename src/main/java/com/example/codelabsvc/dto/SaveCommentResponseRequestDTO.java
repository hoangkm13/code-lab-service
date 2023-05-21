package com.example.codelabsvc.dto;


import lombok.Data;

@Data
public class SaveCommentResponseRequestDTO {
    private String commentId;
    private String text;

}
