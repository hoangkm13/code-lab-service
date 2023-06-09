package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.constant.Language;
import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.*;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.multithread.ExecutionFactory;
import com.example.codelabsvc.repository.BookmarkedChallengeRepository;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TestCaseRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.ChallengeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Value("${compile.url}")
    private String compileUrl;
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
    public Challenge createChallenge(ChallengeDTO challengeDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Challenge challenge = new Challenge();

        if (challengeRepository.existsByName(challengeDTO.getName())) {
            throw new CustomException(ErrorCode.CHALLENGE_EXIST);
        }

        challenge.setId(UUID.randomUUID().toString());
        challenge.setName(challengeDTO.getName());
        challenge.setIssue(challengeDTO.getIssue());
        challenge.setSkill(challengeDTO.getSkill());
        challenge.setPoints(challengeDTO.getPoints());
        challenge.setDifficulty(challengeDTO.getDifficulty());
        challenge.setSubDomain(challengeDTO.getSubDomain());

        if (CollectionUtils.isNotEmpty(challengeDTO.getTestCaseIds())) {
            var listTestCase = this.testCaseRepository.findTestCasesByTestCaseIds(challengeDTO.getTestCaseIds());
            if (listTestCase.size() != challengeDTO.getTestCaseIds().size()) {
                throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
            }

            challenge.setTestCaseIds(challengeDTO.getTestCaseIds());
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

    @Override
    public List<TestCase> submitCode(String language, String challengeId, MultipartFile submittedSourceCode) throws CustomException {
        if (!Objects.equals(language, Language.valueOf(language).toString().split(" ")[0])) {
            throw new CustomException(ErrorCode.LANGUAGE_ERROR);
        }

        var challenge = getChallengeById(challengeId);
        List<Future<TestCase>> futureList = new ArrayList<>();
        List<TestCase> testCasesResult = new ArrayList<>();

        List<String> testCaseIds = challenge.getTestCaseIds();
        List<TestCase> testCases = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(testCaseIds)) {
            testCases = this.testCaseRepository.findTestCasesByTestCaseIds(testCaseIds);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(testCases.size());

        Callable<TestCase> callable;
        Future<TestCase> future;

        for (TestCase testCase : testCases) {
            callable = new ExecutionFactory(compileUrl, language, submittedSourceCode, testCase, testCaseRepository);
            future = executorService.submit(callable);

            futureList.add(future);
        }

        executorService.shutdown();

        for (Future<TestCase> f : futureList) {
            try {
                testCasesResult.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        checkResult(testCasesResult, challenge);

        return testCasesResult;
    }

    //Check result after submit code
    private void checkResult(List<TestCase> testCases, Challenge challenge) {
        int size = (int) testCases.stream().filter(tc -> tc.getVerdict().equals("Accepted")).count();

        if (size == testCases.size()) {

        }
    }

    @Override
    public Challenge updateChallenge(ChallengeDTO challengeDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Challenge challenge = getChallengeById(challengeDTO.getId());

        challenge.setName(challengeDTO.getName() != null ? challengeDTO.getName() : challenge.getName());
        challenge.setIssue(challengeDTO.getName() != null ? challengeDTO.getName() : challenge.getName());
        challenge.setSkill(challengeDTO.getSkill() != null ? challengeDTO.getSkill() : challenge.getSkill());
        challenge.setPoints(challengeDTO.getPoints() != null ? challengeDTO.getPoints() : challenge.getPoints());
        challenge.setDifficulty(challengeDTO.getDifficulty() != null ? challengeDTO.getDifficulty() : challenge.getDifficulty());
        challenge.setSubDomain(challengeDTO.getSubDomain() != null ? challengeDTO.getSubDomain() : challenge.getSubDomain());

        if (CollectionUtils.isNotEmpty(challengeDTO.getTestCaseIds())) {
            var listTestCase = this.testCaseRepository.findTestCasesByTestCaseIds(challengeDTO.getTestCaseIds());
            if (listTestCase.size() != challengeDTO.getTestCaseIds().size()) {
                throw new CustomException(ErrorCode.TESTCASE_NOT_EXISTED_OR_INVALID);
            }

            for (String newTestCaseId : challengeDTO.getTestCaseIds()) {
                if (!challenge.getTestCaseIds().contains(newTestCaseId)) {
                    challenge.getTestCaseIds().add(newTestCaseId);
                }
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
    public List<Challenge> filterChallenge(Map<String, List<String>> fieldValues) {
        Query query = new Query();

        // Check if "status" field is provided and filter accordingly
        if (fieldValues.containsKey("status")) {
            List<String> statusValues = fieldValues.get("status");

            if (statusValues.contains("SOLVED") && statusValues.contains("UNSOLVED")) {
                // Show all challenges
            } else if (statusValues.contains("SOLVED")) {
                query.addCriteria(Criteria.where("id").in(getSolvedChallengeIds()));
            } else if (statusValues.contains("UNSOLVED")) {
                query.addCriteria(Criteria.where("id").nin(getSolvedChallengeIds()));
            }
        }

        // Check if "skill" field is provided and filter accordingly
        if (fieldValues.containsKey("skill")) {
            List<String> skillValues = fieldValues.get("skill");
            Criteria skillCriteria = Criteria.where("skill").in(skillValues);
            query.addCriteria(skillCriteria);
        }

        // Check if "difficulty" field is provided and filter accordingly
        if (fieldValues.containsKey("difficulty")) {
            List<String> difficultyValues = fieldValues.get("difficulty");
            Criteria difficultyCriteria = Criteria.where("difficulty").in(difficultyValues);
            query.addCriteria(difficultyCriteria);
        }

        // Check if "subDomain" field is provided and filter accordingly
        if (fieldValues.containsKey("subDomain")) {
            List<String> subDomainValues = fieldValues.get("subDomain");
            Criteria subDomainCriteria = Criteria.where("subDomain").in(subDomainValues);
            query.addCriteria(subDomainCriteria);
        }

        return mongoTemplate.find(query, Challenge.class);
    }

    private List<String> getSolvedChallengeIds() {
        Query solvedQuery = new Query(Criteria.where("status").is("SOLVED"));
        solvedQuery.fields().include("challengeId");

        List<UserChallenge> solvedChallenges = mongoTemplate.find(solvedQuery, UserChallenge.class);

        return solvedChallenges.stream()
                .map(UserChallenge::getChallengeId)
                .collect(Collectors.toList());
    }
}
