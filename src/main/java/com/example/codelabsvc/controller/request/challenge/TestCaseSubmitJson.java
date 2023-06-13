package com.example.codelabsvc.controller.request.challenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseSubmitJson {
    @NotNull
    private String submittedSourceCode;
}
