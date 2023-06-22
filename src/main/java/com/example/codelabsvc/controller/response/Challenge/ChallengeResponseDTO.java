package com.example.codelabsvc.controller.response.Challenge;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import com.example.codelabsvc.entity.Challenge;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChallengeResponseDTO {
    private Challenge challenge;
    private Status status;
    private Integer totalPointOfTopic;
    private Integer totalPointOfUser;

}
