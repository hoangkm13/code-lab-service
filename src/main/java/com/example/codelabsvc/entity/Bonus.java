package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bonus")

public class Bonus extends EntityBase {

    private String id;

    private Integer point;

    private String task;

    private String challengeId;
}
