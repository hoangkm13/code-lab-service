package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.request.testCase.CreateTestCaseDTO;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.TestCaseRepository;
import com.example.codelabsvc.service.TestCaseService;
import com.example.codelabsvc.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public TestCase createTestCase(CreateTestCaseDTO createTestCaseDTO) throws CustomException, IOException {

        var existedTestCase = this.testCaseRepository.findByName(createTestCaseDTO.getName());

        if (existedTestCase != null) {
            throw new CustomException(ErrorCode.TESTCASE_EXISTED);
        }

        TestCase testCase = new TestCase();

        String testCaseId = UUID.randomUUID().toString();
        var result = fileUtils.buildMultipartFile(createTestCaseDTO.getInputContent(), createTestCaseDTO.getExpectedOutputContent(), testCaseId);

        testCase.setId(testCaseId);
        testCase.setName(createTestCaseDTO.getName());
        testCase.setMemoryLimit(createTestCaseDTO.getMemoryLimit());
        testCase.setCompilerMessage(createTestCaseDTO.getCompilerMessage());
        testCase.setComplicationDuration(createTestCaseDTO.getComplicationDuration());
        testCase.setTimeLimit(createTestCaseDTO.getTimeLimit());
        testCase.setExpectedOutputFilePath(result.getExpectedOutputFilePath());
        testCase.setInputFilePath(result.getInputFilePath());

        this.testCaseRepository.save(testCase);

        return testCase;
    }
}
