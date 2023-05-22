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
@Document(collection = "discussion")

public class Discussion extends EntityBase {

    private String id;

    private String name;

    private String challengeId;

    private List<Comment> commentList;
}
