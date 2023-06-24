package com.example.codelabsvc.controller.response.challenge;

import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.entity.Challenge;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChallengeResponseDTO {
    private Challenge challenge;

    private Status status;

}
