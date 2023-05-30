package com.example.codelabsvc.service;

import com.example.codelabsvc.constant.Difficulty;
import com.example.codelabsvc.constant.Skill;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.constant.Subdomain;
import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChallengeService {

    Challenge createChallenge(ChallengeDTO challengeDTO) throws CustomException;

    List<Challenge> getAllChallengesByTopic(String topicId) throws CustomException;

    Challenge getChallengeById(String id) throws CustomException;

    List<TestCase> submitCode(String language, String challengeId, MultipartFile sourceCode) throws CustomException;

    Challenge bookmarkChallenge(String id, boolean bookmarkStatus) throws CustomException;

    List<Challenge> listAllBookmarkChallenge();

    List<Challenge> filterChallenge(Status status, Skill skill, Difficulty difficulty, Subdomain subdomain);

    Challenge updateChallenge(ChallengeDTO challengeDTO) throws CustomException;

    Challenge deleteChallenge(String id) throws CustomException;
}
