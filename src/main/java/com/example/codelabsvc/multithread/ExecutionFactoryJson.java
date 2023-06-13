package com.example.codelabsvc.multithread;

import com.example.codelabsvc.controller.request.challenge.TestCaseSubmitJson;
import com.example.codelabsvc.controller.response.testCase.TestCaseJsonResponse;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.repository.TestCaseRepository;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

public class ExecutionFactoryJson implements Callable<TestCaseJsonResponse> {
    private final String userId;
    private final TestCase testCase;
    private final String compileJsonUrl;
    private final TestCaseSubmitJson testCaseSubmitJson;

    private final TestCaseRepository testCaseRepository;

    public ExecutionFactoryJson(String userId, TestCase testCase, String compileJsonUrl, TestCaseSubmitJson testCaseSubmitJson, TestCaseRepository testCaseRepository) {
        this.userId = userId;
        this.testCase = testCase;
        this.compileJsonUrl = compileJsonUrl;
        this.testCaseSubmitJson = testCaseSubmitJson;
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    public TestCaseJsonResponse call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(10) * 100);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var challengeSubmitJson = testCase.getChallengeSubmitJson();
        challengeSubmitJson.setSourcecode(testCaseSubmitJson.getSubmittedSourceCode());

        HttpEntity requestEntity = new HttpEntity(challengeSubmitJson, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                Objects.requireNonNull(compileJsonUrl),
                HttpMethod.POST,
                requestEntity,
                Object.class
        );

        LinkedHashMap linkedHashMap = (LinkedHashMap) response.getBody();

        TestCaseJsonResponse testCaseJsonResponse = new TestCaseJsonResponse();
        Class<?> objClass = testCaseJsonResponse.getClass();
        Field[] fields = objClass.getDeclaredFields();

        if (linkedHashMap != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                if (linkedHashMap.containsKey(field.getName())) {
                    field.set(testCaseJsonResponse, linkedHashMap.get(field.getName()));
                }
            }
        }

        TestCase testCaseByUserId = new TestCase();
        testCaseByUserId.setId(UUID.randomUUID().toString());
        testCaseByUserId.setTestCaseJsonResponse(testCaseJsonResponse);
        testCaseByUserId.setChallengeSubmitJson(challengeSubmitJson);
        testCaseByUserId.setChallengeId(testCase.getChallengeId());
        testCaseByUserId.setName(testCase.getName().split("-")[0] + "result by: " + userId);
        testCaseByUserId.setUserId(userId);

        this.testCaseRepository.save(testCaseByUserId);

        return testCaseByUserId.getTestCaseJsonResponse();
    }
}
