package com.example.codelabsvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveChildCommentRequestDTO {
    private String parentId;
    private String challengeId;
    private String text;
}
