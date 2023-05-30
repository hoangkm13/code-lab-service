package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.*;
import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.multithread.ExecutionFactory;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TestCaseRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.ChallengeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Value("${compile.url}")
    private String compileUrl;
    private final TopicRepository topicRepository;

    private final ChallengeRepository challengeRepository;
    private final TestCaseRepository testCaseRepository;

    private final MongoTemplate mongoTemplate;

    public ChallengeServiceImpl(TopicRepository topicRepository, ChallengeRepository challengeRepository, TestCaseRepository testCaseRepository, MongoTemplate mongoTemplate) {
        this.topicRepository = topicRepository;
        this.challengeRepository = challengeRepository;
        this.testCaseRepository = testCaseRepository;
        this.mongoTemplate = mongoTemplate;
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
        challenge.setStatus(challengeDTO.getStatus());


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
    public List<Challenge> getAllChallengesByTopic(String topicId) throws CustomException {
        var topic = topicRepository.findById(topicId);

        if(topic.isEmpty()){
            throw new CustomException(ErrorCode.TOPIC_NOT_EXIST);
        }

        List<Challenge> challenges = new ArrayList<>();

        for (String id: topic.get().getChallengeIds()){
            Challenge challenge = getChallengeById(id);
            challenges.add(challenge);
        }

        return challenges;
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

        return testCasesResult;
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
        challenge.setStatus(challengeDTO.getStatus() != null ? challengeDTO.getStatus() : challenge.getStatus());

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

        challenge.setBookmark(challengeDTO.isBookmark());
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
    public Challenge bookmarkChallenge(String id, boolean bookmarkStatus) throws CustomException {
        Challenge challenge = getChallengeById(id);
        challenge.setBookmark(bookmarkStatus);
        return challengeRepository.save(challenge);
    }

    @Override
    public List<Challenge> listAllBookmarkChallenge() {
        return challengeRepository.findAllByBookmark(true);
    }

    @Override
    public List<Challenge> filterChallenge(Status status, Skill skill, Difficulty difficulty, Subdomain subdomain) {
        Criteria criteria = new Criteria();

        if (status != null) criteria.and("status").is(status);
        if (skill != null) criteria.and("skill").is(skill);
        if (difficulty != null) criteria.and("difficulty").is(difficulty);
        if (subdomain != null) criteria.and("subDomain").is(subdomain);

        Query query = new Query(criteria);

        return mongoTemplate.findAll(Challenge.class, query.toString());
    }
}
