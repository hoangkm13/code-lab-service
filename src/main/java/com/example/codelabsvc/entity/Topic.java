package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Topic extends EntityBase {
    @Id
    private String id;

    private String name;

    private List<Challenge> challenges;

    private String description;

    private Integer totalPoints;

    private Integer presentPoints;


}
