package com.example.codelabsvc.controller.response.Challenge;

import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChallengeListAndPointResponseDTO {
    private List<Challenge> challenge;
    private Integer totalPointOfTopic;
    private Integer totalPointOfUser;

    public ChallengeListAndPointResponseDTO(List<Challenge> challenge, Integer totalPointOfTopic, Integer totalPointOfUser) {
        this.challenge = challenge;
        this.totalPointOfTopic = totalPointOfTopic;
        this.totalPointOfUser = totalPointOfUser;
    }
}
