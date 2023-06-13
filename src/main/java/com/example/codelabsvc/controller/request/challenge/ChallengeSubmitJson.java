package com.example.codelabsvc.controller.request.challenge;

import com.example.codelabsvc.controller.request.testCase.TestCaseJsonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeSubmitJson {

    private String sourcecode;

    @NotNull
    private String language;

    @NotNull
    private Integer timeLimit;

    @NotNull
    private Integer memoryLimit;

    @NotNull
    private LinkedHashMap<String, TestCaseJsonDTO> testCases; // Note: test cases should be given in order
}
