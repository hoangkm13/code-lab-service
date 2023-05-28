package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "test-case")
public class TestCase {

    private String id;

    private String name;

    private String compilerMessage;

    private String output;

    private String inputFilePath;

    private String expectedOutputFilePath;

    private int complicationDuration;

    private int timeLimit;

    private int memoryLimit;

}
