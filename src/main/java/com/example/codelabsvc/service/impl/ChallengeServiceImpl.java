package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public List<Challenge> getAllChallengesByTopic(String topicId) {
        var topic = topicRepository.findById(topicId);

        return topic.map(Topic::getChallenges).orElse(null);
    }

    @Override
    public Challenge getChallengeById(String id) {
        var challenge = challengeRepository.findById(id);

        return challenge.orElse(null);
    }


}
