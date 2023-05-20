package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comment")

public class Comment extends EntityBase {
    @Id
    private String id;

    private User user;

    private String commentText;

    private String createdAt;
}
