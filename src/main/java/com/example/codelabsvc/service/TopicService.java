package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.topic.TopicDTO;
import com.example.codelabsvc.controller.response.topic.ListTopicsPercentResponse;
import com.example.codelabsvc.controller.response.topic.MostPointTopicResponse;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.entity.UserTopic;
import com.example.codelabsvc.exception.CustomException;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    UserTopic getUserTopic(String id, String userId) throws CustomException;

    Topic getTopicById(String id) throws CustomException;

    Topic createTopic(TopicDTO topicDTO) throws CustomException;

    Topic updateTopic(TopicDTO topicDTO) throws CustomException;

    Topic deleteTopic(String id) throws CustomException;

    List<UserTopic> ranking(String topicId);

    List<ListTopicsPercentResponse> getAllTopicWithPoint() throws CustomException;

    List<MostPointTopicResponse> getMostPointTopics() throws CustomException;
}