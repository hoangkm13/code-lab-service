package com.example.codelabsvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "star")
public class Star {
    private String id;

    private int value; //value = 1, 2, 3, 4

    private int validPoint;
}
