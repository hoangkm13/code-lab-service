package com.example.codelabsvc.entity;

import com.example.codelabsvc.constant.Language;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "pre-script")

public class PreScript {

    private String id;

    private String challengeId;

    private String preContent;

    private Language language;
}
