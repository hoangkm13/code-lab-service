package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Bonus extends EntityBase {
    @Id
    private String id;

    private Integer point;

    private String task;

    private String challengeId;
}
