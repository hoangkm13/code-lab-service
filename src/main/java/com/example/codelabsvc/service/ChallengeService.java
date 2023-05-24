package com.example.codelabsvc.service;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import com.example.codelabsvc.controller.request.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;

import java.util.List;

public interface ChallengeService {

    Challenge createChallenge(ChallengeDTO challengeDTO) throws CustomException;

    List<Challenge> getAllChallengesByTopic(String topicId) throws CustomException;

    Challenge getChallengeById(String id) throws CustomException;

    List<TestCase> submitCode(PreScript preScript, String challengeId);

    Challenge bookmarkChallenge(String id, boolean bookmarkStatus) throws CustomException;

    List<Challenge> listAllBookmarkChallenge();

    List<Challenge> filterChallenge(Status status, Skill skill, Difficulty difficulty, Subdomain subdomain);

    Challenge updateChallenge(String id, ChallengeDTO challengeDTO) throws CustomException;
}
