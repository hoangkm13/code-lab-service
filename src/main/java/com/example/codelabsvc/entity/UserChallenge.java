package com.example.codelabsvc.entity;

import com.example.codelabsvc.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user-challenge")

public class UserChallenge {

    private String id;

    private String userId;

    private String challengeId;

    private Status status;
}
