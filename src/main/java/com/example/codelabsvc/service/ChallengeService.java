package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.BookmarkedChallenge;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ChallengeService {

    Challenge createChallenge(ChallengeDTO challengeDTO) throws CustomException;

    Page<Challenge> getAllChallengesByTopic(String topicId, int page, int size) throws CustomException;

    Challenge getChallengeById(String id) throws CustomException;

    List<TestCase> submitCode(String language, String challengeId, MultipartFile sourceCode) throws CustomException;

    BookmarkedChallenge changeBookmarkStatus(String id) throws CustomException;

    Page<Challenge> listAllBookmarkChallenge(int page, int size) throws CustomException;

    Challenge updateChallenge(ChallengeDTO challengeDTO) throws CustomException;

    Challenge deleteChallenge(String id) throws CustomException;


    List<Challenge> filterChallenge(Map<String, List<String>> fieldValues);
}
