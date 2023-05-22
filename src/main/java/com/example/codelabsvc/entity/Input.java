package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "input")
public class Input {

    private String id;

    private String name;

    private String path;

    private String content;
}
