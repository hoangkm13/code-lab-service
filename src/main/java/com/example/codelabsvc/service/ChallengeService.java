package com.example.codelabsvc.service;

import com.example.codelabsvc.entity.Challenge;

import java.util.List;

public interface ChallengeService {

    List<Challenge> getAllChallengesByTopic(String topicId);

    Challenge getChallengeById(String id);
}
