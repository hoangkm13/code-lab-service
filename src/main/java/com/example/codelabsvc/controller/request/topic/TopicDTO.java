package com.example.codelabsvc.controller.request.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private String id;

    private String name;

    private List<String> challengeIds;

    private String description;

    private Integer totalPoints;

    private List<String> starIds;
}
