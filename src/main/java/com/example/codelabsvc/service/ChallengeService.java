package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;

import java.util.List;

public interface ChallengeService {

    Challenge createChallenge(ChallengeDTO challengeDTO);

    List<Challenge> getAllChallengesByTopic(String topicId);

    Challenge getChallengeById(String id) throws CustomException;

    List<TestCase> submitCode(PreScript preScript, String challengeId);
}
