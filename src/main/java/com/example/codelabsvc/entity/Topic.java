package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "topic")

public class Topic extends EntityBase {

    private String id;

    private String name;

    private List<Challenge> challenges;

    private String description;

    private Integer totalPoints;

    private Integer presentPoints;
}
