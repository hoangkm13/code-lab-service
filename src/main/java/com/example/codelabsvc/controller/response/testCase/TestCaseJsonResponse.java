package com.example.codelabsvc.controller.response.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseJsonResponse {
    private String verdict;
    private Integer statusCode;
    private String error;
    private LinkedHashMap<String, Object> testCasesResult;
    private Integer compilationDuration;
    private Double averageExecutionDuration;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String language;
    private String dateTime;
}
