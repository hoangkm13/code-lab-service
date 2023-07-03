package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.testCase.CreateTestCaseDTO;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;

import java.io.IOException;
import java.util.List;

public interface TestCaseService {
    TestCase createTestCase(CreateTestCaseDTO createTestCaseDTO) throws IOException, CustomException;

    List<TestCase> getListTestCaseByUserIdAndChallengeId(String challengeId) throws IOException, CustomException;

    TestCase getTestCaseTemplateByChallengeId(String id);

}
