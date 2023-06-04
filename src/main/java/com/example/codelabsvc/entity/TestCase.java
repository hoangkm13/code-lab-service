package com.example.codelabsvc.entity;

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

    private String name;

    private String compilerMessage;

    private String inputFilePath;

    private String expectedOutputFilePath;


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
