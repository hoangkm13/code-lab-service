package com.example.codelabsvc.dto;


import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class SaveCommentRequestDTO {
    private String challengeId;
    private String text;
    private String code;

}
