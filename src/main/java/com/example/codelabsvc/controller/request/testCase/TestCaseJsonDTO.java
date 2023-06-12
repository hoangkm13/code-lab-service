package com.example.codelabsvc.controller.request.testCase;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class TestCaseJsonDTO {

    private String input;

    @NotNull
    private String expectedOutput;
}
