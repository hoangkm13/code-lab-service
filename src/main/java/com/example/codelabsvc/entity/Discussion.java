package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Discussion extends EntityBase {
    @Id
    private String id;

    private String name;

    private String challengeId;

    private List<Comment> commentList;
}
