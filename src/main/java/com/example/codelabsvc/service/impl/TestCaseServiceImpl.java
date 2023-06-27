package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.request.testCase.CreateTestCaseDTO;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.TestCaseRepository;
import com.example.codelabsvc.service.TestCaseService;
import com.example.codelabsvc.util.FileUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;

    private final FileUtils fileUtils;

    public TestCaseServiceImpl(TestCaseRepository testCaseRepository, FileUtils fileUtils) {
        this.testCaseRepository = testCaseRepository;
        this.fileUtils = fileUtils;
    }

    @Override
    public TestCase createTestCase(CreateTestCaseDTO createTestCaseDTO) throws CustomException {

        var existedTestCase = this.testCaseRepository.findByName(createTestCaseDTO.getName());

        if (existedTestCase != null) {
            throw new CustomException(ErrorCode.TESTCASE_EXISTED);
        }

        TestCase testCase = new TestCase();

        String testCaseId = UUID.randomUUID().toString();

        testCase.setId(testCaseId);
        testCase.setName(createTestCaseDTO.getName() + "-template");
        testCase.setChallengeSubmitJson(createTestCaseDTO.getChallengeSubmitJson());
        testCase.setChallengeId(createTestCaseDTO.getChallengeId());

        return testCaseRepository.save(testCase);
    }

    @Override
    public List<TestCase> getListTestCaseByUserIdAndChallengeId(String challengeId) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        var listExistedTestCases = this.testCaseRepository.findTestCaseByChallengeIdAndUserId(challengeId, authentication.getId());

        if (listExistedTestCases.isEmpty()) {
            throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
        }

        return listExistedTestCases;
    }
}
