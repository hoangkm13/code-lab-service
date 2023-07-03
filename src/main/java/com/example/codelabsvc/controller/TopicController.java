package com.example.codelabsvc.controller;

import com.example.codelabsvc.controller.request.topic.TopicDTO;
import com.example.codelabsvc.controller.response.topic.ListTopicsPercentResponse;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.entity.UserTopic;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "", produces = "application/json")
    public ApiResponse<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ApiResponse.successWithResult(topics);
    }

    @GetMapping(value = "/{topicId}", produces = "application/json")
    public ApiResponse<UserTopic> getUserTopic(@Valid @PathVariable String topicId) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        UserTopic userTopic = topicService.getUserTopic(topicId, authentication.getId());
        return ApiResponse.successWithResult(userTopic);
    }

    @GetMapping(value = "ranking/{topicId}", produces = "application/json")
    public ApiResponse<List<UserTopic>> rankingUser(@Valid @PathVariable String topicId) throws CustomException {
        List<UserTopic> userTopic = topicService.ranking(topicId);
        return ApiResponse.successWithResult(userTopic);
    }

    @PostMapping(value = "", produces = "application/json")
    public ApiResponse<Topic> createTopic(@RequestBody TopicDTO topicDTO) throws CustomException {
        Topic topic = topicService.createTopic(topicDTO);
        return ApiResponse.successWithResult(topic);
    }

    @PutMapping(value = "", produces = "application/json")
    public ApiResponse<Topic> updateTopic(@RequestBody TopicDTO topicDTO) throws CustomException {
        Topic topic = topicService.updateTopic(topicDTO);
        return ApiResponse.successWithResult(topic);
    }

    @DeleteMapping(value = "/{topicId}", produces = "application/json")
    public ApiResponse<Topic> deleteChallenge(@Valid @PathVariable String topicId) throws CustomException {
        Topic topic = topicService.deleteTopic(topicId);
        return ApiResponse.successWithResult(topic);
    }

    @GetMapping(value = "/point", produces = "application/json")
    public ApiResponse<List<ListTopicsPercentResponse>> getAllTopicsWithPoint() throws CustomException{
        List<ListTopicsPercentResponse> topics = topicService.getAllTopicWithPoint();
        return ApiResponse.successWithResult(topics);
    }
}
