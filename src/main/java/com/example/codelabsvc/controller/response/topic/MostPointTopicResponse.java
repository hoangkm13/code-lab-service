package com.example.codelabsvc.controller.response.topic;

import com.example.codelabsvc.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostPointTopicResponse {

    private Topic topic;

    private Integer userPoints;
}
