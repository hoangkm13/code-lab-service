package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "bookmarkedChallenge")
public class BookmarkedChallenge {

    private String id;

    private String userId;

    private String challengeId;
}
