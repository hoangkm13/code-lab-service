package com.example.codelabsvc.controller.response.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResponseBytes {
    private String inputFileBytes;

    private String expectedOutputBytes;

}
