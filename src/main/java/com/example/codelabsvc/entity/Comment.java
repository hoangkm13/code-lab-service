package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Comment extends EntityBase {
    @Id
    private String id;

    private User user;

    private String commentText;

}
