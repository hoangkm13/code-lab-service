package com.example.codelabsvc.entity;

import com.example.codelabsvc.controller.request.challenge.ChallengeSubmitJson;
import com.example.codelabsvc.controller.response.testCase.TestCaseJsonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "test-case")
public class TestCase {

    private String id;

    private String challengeId;

    private String userId;

    private String name;

    private ChallengeSubmitJson challengeSubmitJson;

    private TestCaseJsonResponse testCaseJsonResponse;

}
