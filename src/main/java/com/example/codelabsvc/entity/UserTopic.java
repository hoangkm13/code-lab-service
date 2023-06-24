package com.example.codelabsvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user-topic")

public class UserTopic {

    private String id;

    private String userId;

    private String topicId;

    private Integer userPoints;

    private Integer totalPoints;
}
