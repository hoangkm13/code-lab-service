package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.constant.Status;
import com.example.codelabsvc.controller.request.challenge.CreateChallengeDTO;
import com.example.codelabsvc.controller.request.challenge.TestCaseSubmitJson;
import com.example.codelabsvc.controller.request.challenge.UpdateChallengeDTO;
import com.example.codelabsvc.controller.response.Challenge.ChallengeResponseDTO;
import com.example.codelabsvc.controller.response.testCase.TestCaseJsonResponse;
import com.example.codelabsvc.entity.BookmarkedChallenge;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.entity.UserChallenge;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.multithread.ExecutionFactoryJson;
import com.example.codelabsvc.repository.*;
import com.example.codelabsvc.service.ChallengeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Value("${compile.url}")
    private String compileUrl;

    @Value("${compile.json.url}")
    private String compileJsonUrl;
    private final TopicRepository topicRepository;

    private final ChallengeRepository challengeRepository;
    private final TestCaseRepository testCaseRepository;

    private final BookmarkedChallengeRepository bookmarkedChallengeRepository;

    private final MongoTemplate mongoTemplate;

    public ChallengeServiceImpl(TopicRepository topicRepository,
                                ChallengeRepository challengeRepository,
                                TestCaseRepository testCaseRepository,
                                MongoTemplate mongoTemplate,
                                BookmarkedChallengeRepository bookmarkedChallengeRepository) {
        this.topicRepository = topicRepository;
        this.challengeRepository = challengeRepository;
        this.testCaseRepository = testCaseRepository;
        this.mongoTemplate = mongoTemplate;
        this.bookmarkedChallengeRepository = bookmarkedChallengeRepository;
    }


    @Override
    public Challenge createChallenge(CreateChallengeDTO createChallengeDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Challenge challenge = new Challenge();

        if (challengeRepository.existsByName(createChallengeDTO.getName())) {
            throw new CustomException(ErrorCode.CHALLENGE_EXIST);
        }

        challenge.setId(UUID.randomUUID().toString());
        challenge.setName(createChallengeDTO.getName());
        challenge.setIssue(createChallengeDTO.getIssue());
        challenge.setSkill(createChallengeDTO.getSkill());
        challenge.setPoints(createChallengeDTO.getPoints());
        challenge.setDifficulty(createChallengeDTO.getDifficulty());
        challenge.setSubDomain(createChallengeDTO.getSubDomain());

        if (createChallengeDTO.getTestCaseId() != null) {
            var existedTestCase = this.testCaseRepository.findById(createChallengeDTO.getTestCaseId());
            if (existedTestCase.isEmpty()) {
                throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
            }
        }

        challenge.setCreatedAt(LocalDate.now().toString());
        challenge.setCreatedBy(authentication.getId());
        //Todo bonuses
        return challengeRepository.save(challenge);
    }

    @Override
    public Page<Challenge> getAllChallengesByTopic(String topicId, int page, int size) throws CustomException {
        Pageable pageable = PageRequest.of(page, size);
        var topic = topicRepository.findById(topicId);

        if (topic.isEmpty()) {
            throw new CustomException(ErrorCode.TOPIC_NOT_EXIST);
        }

        List<Challenge> challenges = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(topic.get().getChallengeIds())) {
            var challengeList = this.challengeRepository.findChallengesByChallengeIds(topic.get().getChallengeIds());
            if (challengeList.size() != topic.get().getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }
            challenges = challengeList;

        }

        return new PageImpl<>(challenges, pageable, challenges.size());
    }

    @Override
    public Challenge getChallengeById(String id) throws CustomException {
        var challenge = challengeRepository.findById(id);

        return challenge.orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID));
    }

//    @Override
//    public List<TestCase> submitCode(String language, String challengeId, MultipartFile submittedSourceCode) throws CustomException {
//        if (!Objects.equals(language, Language.valueOf(language).toString().split(" ")[0])) {
//            throw new CustomException(ErrorCode.LANGUAGE_ERROR);
//        }
//
//        var challenge = getChallengeById(challengeId);
//        List<Future<TestCase>> futureList = new ArrayList<>();
//        List<TestCase> testCasesResult = new ArrayList<>();
//
//        List<String> testCaseIds = challenge.getTestCaseIds();
//        List<TestCase> testCases = new ArrayList<>();
//
//        if (CollectionUtils.isNotEmpty(testCaseIds)) {
//            testCases = this.testCaseRepository.findTestCasesByTestCaseIds(testCaseIds);
//        }
//
//        ExecutorService executorService = Executors.newFixedThreadPool(testCases.size());
//
//        Callable<TestCase> callable;
//        Future<TestCase> future;
//
//        for (TestCase testCase : testCases) {
//            callable = new ExecutionFactory(compileUrl, language, submittedSourceCode, testCase, testCaseRepository);
//            future = executorService.submit(callable);
//
//            futureList.add(future);
//        }
//
//        executorService.shutdown();
//
//        for (Future<TestCase> f : futureList) {
//            try {
//                testCasesResult.add(f.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return testCasesResult;
//}

    @Override
    public TestCaseJsonResponse submitCodeJson(String testCaseId, TestCaseSubmitJson testCaseSubmitJson) throws CustomException, ExecutionException, InterruptedException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        var checkExistedTestCase = testCaseRepository.findById(testCaseId);
        if (checkExistedTestCase.isEmpty()) {
            throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
        }

        var existedTestCase = checkExistedTestCase.get();
        if (existedTestCase.getUserId() != null) {
            throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<TestCaseJsonResponse> callable;
        Future<TestCaseJsonResponse> future;

        callable = new ExecutionFactoryJson(authentication.getId(), existedTestCase, compileJsonUrl, testCaseSubmitJson, testCaseRepository);
        future = executorService.submit(callable);

        executorService.shutdown();

        return future.get();
    }

    @Override
    public Challenge updateChallenge(UpdateChallengeDTO updateChallengeDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Challenge challenge = getChallengeById(updateChallengeDTO.getChallengeId());

        challenge.setName(updateChallengeDTO.getName() != null ? updateChallengeDTO.getName() : challenge.getName());
        challenge.setIssue(updateChallengeDTO.getName() != null ? updateChallengeDTO.getName() : challenge.getName());
        challenge.setSkill(updateChallengeDTO.getSkill() != null ? updateChallengeDTO.getSkill() : challenge.getSkill());
        challenge.setPoints(updateChallengeDTO.getPoints() != null ? updateChallengeDTO.getPoints() : challenge.getPoints());
        challenge.setDifficulty(updateChallengeDTO.getDifficulty() != null ? updateChallengeDTO.getDifficulty() : challenge.getDifficulty());
        challenge.setSubDomain(updateChallengeDTO.getSubDomain() != null ? updateChallengeDTO.getSubDomain() : challenge.getSubDomain());

        if (updateChallengeDTO.getTestCaseId() != null) {
            var existedTestCase = this.testCaseRepository.findById(updateChallengeDTO.getTestCaseId());
            if (existedTestCase.isEmpty()) {
                throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
            }
        }

        challenge.setUpdateBy(authentication.getUsername());
        challenge.setUpdatedAt(LocalDate.now().toString());

        return challengeRepository.save(challenge);
    }

    @Override
    public Challenge deleteChallenge(String id) throws CustomException {
        Challenge challenge = getChallengeById(id);
        challengeRepository.delete(challenge);
        return challenge;
    }

    @Override
    public BookmarkedChallenge changeBookmarkStatus(String id) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Challenge challenge = getChallengeById(id);

        BookmarkedChallenge bookmarkedChallenge;

        if (!bookmarkedChallengeRepository.existsByUserIdAndChallengeId(authentication.getId(), challenge.getId())) {
            bookmarkedChallenge = new BookmarkedChallenge();

            bookmarkedChallenge.setChallengeId(challenge.getId());
            bookmarkedChallenge.setUserId(authentication.getId());

            return bookmarkedChallengeRepository.save(bookmarkedChallenge);
        } else {
            bookmarkedChallenge = bookmarkedChallengeRepository.findByUserIdAndChallengeId(authentication.getId(), challenge.getId());

            bookmarkedChallengeRepository.delete(bookmarkedChallenge);

            return bookmarkedChallenge;
        }
    }

    @Override
    public Page<Challenge> listAllBookmarkChallenge(int page, int size) throws CustomException {
        Pageable pageable = PageRequest.of(page, size);

        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        List<BookmarkedChallenge> bookmarkedChallenges = bookmarkedChallengeRepository.findAllByUserId(authentication.getId());

        List<Challenge> challenges = new ArrayList<>();

        for (BookmarkedChallenge c : bookmarkedChallenges) {
            Challenge challenge = getChallengeById(c.getChallengeId());

            challenges.add(challenge);
        }

        return new PageImpl<>(challenges, pageable, challenges.size());
    }

    @Override
    public List<ChallengeResponseDTO> filterChallenge(Map<String, List<String>> fieldValues) {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Query query = new Query();

        // Check if "status" field is provided and filter accordingly
        for (Map.Entry<String, List<String>> f : fieldValues.entrySet()) {
            List<String> values = f.getValue();
            if (!values.isEmpty()) {
                if (Objects.equals(f.getKey(), "status")) {
                    if (values.contains("SOLVED") && values.contains("UNSOLVED")) {
                        // Show all challenges
                    } else if (values.contains("SOLVED")) {
                        query.addCriteria(Criteria.where("id").in(getSolvedChallengeIds(authentication.getId())));
                    } else if (values.contains("UNSOLVED")) {
                        query.addCriteria(Criteria.where("id").nin(getSolvedChallengeIds(authentication.getId())));
                    }
                } else {
                    Criteria criteria = Criteria.where(f.getKey()).in(values);
                    query.addCriteria(criteria);
                }
            }
        }

        List<Challenge> challenges = mongoTemplate.find(query, Challenge.class);
        List<ChallengeResponseDTO> challengeResponseDTOList = new ArrayList<>();

        for (Challenge challenge : challenges) {
            ChallengeResponseDTO challengeResponseDTO = new ChallengeResponseDTO();
            if (getSolvedChallengeIds(authentication.getId()).contains(challenge.getId())) {
                challengeResponseDTO.setStatus(Status.SOLVED);
            } else {
                challengeResponseDTO.setStatus(Status.UNSOLVED);
            }
            challengeResponseDTO.setChallenge(challenge);
            challengeResponseDTOList.add(challengeResponseDTO);
        }

        return challengeResponseDTOList
                .stream()
                .sorted(Comparator.comparing(ChallengeResponseDTO::getStatus))
                .collect(Collectors.toList());
    }

    private List<String> getSolvedChallengeIds(String userId) {
        Query solvedQuery = new Query(Criteria.where("status").is("SOLVED").and("userId").is(userId));
        solvedQuery.fields().include("challengeId");

        List<UserChallenge> solvedChallenges = mongoTemplate.find(solvedQuery, UserChallenge.class);

        return solvedChallenges.stream()
                .map(UserChallenge::getChallengeId)
                .collect(Collectors.toList());
    }
}
