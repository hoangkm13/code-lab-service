package com.example.codelabsvc.controller.request.testCase;

import com.example.codelabsvc.controller.request.challenge.ChallengeSubmitJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestCaseDTO {

    private String id;

    @NotNull
    private String name;

    private String userId;

    @NotNull
    private ChallengeSubmitJson challengeSubmitJson;

    @NotNull
    private String challengeId;

}
