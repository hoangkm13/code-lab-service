package com.example.codelabsvc.controller.response.topic;

import com.example.codelabsvc.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTopicsPercentResponse {
    private Topic topic;

    private int percentValue;
}
