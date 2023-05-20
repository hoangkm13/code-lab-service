package com.example.codelabsvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "testCase")
public class TestCase {

    @Id
    private String id;

    private String compilerMessage;

    private String input;

    private String output;

    private String expectedOutput;

    private String complicationDuration;

}
