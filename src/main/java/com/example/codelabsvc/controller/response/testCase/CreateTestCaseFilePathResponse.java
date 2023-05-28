package com.example.codelabsvc.controller.response.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestCaseFilePathResponse {
    private String inputFilePath;

    private String expectedOutputFilePath;

}
