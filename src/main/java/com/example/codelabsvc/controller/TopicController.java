package com.example.codelabsvc.controller;

import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "/", produces = "application/json")
    public ApiResponse<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ApiResponse.successWithResult(topics);
    }

}
