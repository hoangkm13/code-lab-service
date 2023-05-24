package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
