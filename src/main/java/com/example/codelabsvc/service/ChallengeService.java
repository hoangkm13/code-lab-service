package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.challenge.CreateChallengeDTO;
import com.example.codelabsvc.controller.request.challenge.FilterChallengeRequest;
import com.example.codelabsvc.controller.request.challenge.TestCaseSubmitJson;
import com.example.codelabsvc.controller.request.challenge.UpdateChallengeDTO;
import com.example.codelabsvc.controller.response.challenge.ChallengeResponseDTO;
import com.example.codelabsvc.controller.response.testCase.TestCaseJsonResponse;
import com.example.codelabsvc.entity.BookmarkedChallenge;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ChallengeService {

    List<Challenge> searchChallenge(String challengeName) throws CustomException;
    Challenge createChallenge(CreateChallengeDTO createChallengeDTO) throws CustomException;

    Page<ChallengeResponseDTO> getAllChallengesByTopic(String topicId, int page, int size) throws CustomException;

    Challenge getChallengeById(String id) throws CustomException;

//    List<TestCase> submitCode(String language, String challengeId, MultipartFile sourceCode) throws CustomException;

    TestCaseJsonResponse submitCodeJson(String testCaseId, TestCaseSubmitJson testCaseSubmitJson) throws CustomException, ExecutionException, InterruptedException;

    BookmarkedChallenge changeBookmarkStatus(String id) throws CustomException;

    Page<Challenge> listAllBookmarkChallenge(int page, int size) throws CustomException;

    Challenge updateChallenge(UpdateChallengeDTO updateChallengeDTO) throws CustomException;

    Challenge deleteChallenge(String id) throws CustomException;

    Page<ChallengeResponseDTO> filterChallenge(FilterChallengeRequest filterChallengeRequest);
}
