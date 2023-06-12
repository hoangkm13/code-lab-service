package com.example.codelabsvc.controller;


import com.example.codelabsvc.controller.request.testCase.CreateTestCaseDTO;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/test-case")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @PostMapping(produces = "application/json")
    public ApiResponse<TestCase> createTestCase(@Valid @RequestBody CreateTestCaseDTO createTestCase) throws CustomException, IOException {
        TestCase testCase = testCaseService.createTestCase(createTestCase);
        return ApiResponse.successWithResult(testCase);
    }

    @GetMapping(value = "{challengeId}", produces = "application/json")
    public ApiResponse<List<TestCase>> getListTestCaseByUserIdAndChallengeId(@PathVariable String challengeId) throws CustomException, IOException {
        List<TestCase> testCases = testCaseService.getListTestCaseByUserIdAndChallengeId(challengeId);
        return ApiResponse.successWithResult(testCases);
    }

}
