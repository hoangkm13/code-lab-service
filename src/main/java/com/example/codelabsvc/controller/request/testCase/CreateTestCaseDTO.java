package com.example.codelabsvc.controller.request.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestCaseDTO {

    private String id;

    private String name;

    private String compilerMessage;

    private int complicationDuration;

    private int timeLimit;

    private int memoryLimit;

    private String expectedOutputContent;

    private String inputContent;

}
