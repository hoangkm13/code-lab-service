package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.*;
import com.example.codelabsvc.controller.request.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.multithread.ExecutionFactory;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.ChallengeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Challenge createChallenge(ChallengeDTO challengeDTO) throws CustomException {
        Challenge challenge = new Challenge();

        if(challengeRepository.existsByName(challengeDTO.getName())){
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
        challenge.setTestCases(challengeDTO.getTestCases());

        return challengeRepository.save(challenge);
    }

    @Override
    public List<Challenge> getAllChallengesByTopic(String topicId) throws CustomException {
        var topic = topicRepository.findById(topicId);

        return topic.map(Topic::getChallenges).orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_EXIST));
    }

    @Override
    public Challenge getChallengeById(String id) throws CustomException {
        var challenge = challengeRepository.findById(id);

        return challenge.orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_EXIST));
    }

    @Override
    public List<TestCase> submitCode(PreScript preScript, String challengeId) {
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
        List<Future<TestCase>> futureList = new ArrayList<>();
        List<TestCase> testCasesResult = new ArrayList<>();

        if (challenge.isPresent()) {

            List<TestCase> testCases = challenge.get().getTestCases();
            ExecutorService executorService = Executors.newFixedThreadPool(testCases.size());

            Callable<TestCase> callable;
            Future<TestCase> future;

            for (TestCase testCase : testCases) {
                callable = new ExecutionFactory(preScript, testCase);
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
        }
        return testCasesResult;
    }

    @Override
    public Challenge updateChallenge(String id, ChallengeDTO challengeDTO) throws CustomException {
        Challenge challenge = getChallengeById(id);

        challenge.setName(challengeDTO.getName() != null ? challengeDTO.getName() : challenge.getName());
        challenge.setIssue(challengeDTO.getName() != null ? challengeDTO.getName() : challenge.getName());
        challenge.setSkill(challengeDTO.getSkill() != null ? challengeDTO.getSkill() : challenge.getSkill());
        challenge.setPoints(challengeDTO.getPoints() != null ? challengeDTO.getPoints() : challenge.getPoints());
        challenge.setDifficulty(challengeDTO.getDifficulty() != null ? challengeDTO.getDifficulty() : challenge.getDifficulty());
        challenge.setSubDomain(challengeDTO.getSubDomain() != null ? challengeDTO.getSubDomain() : challenge.getSubDomain());
        challenge.setStatus(challengeDTO.getStatus() != null ? challengeDTO.getStatus() : challenge.getStatus());
        challenge.setTestCases(challengeDTO.getTestCases() != null ? challengeDTO.getTestCases() : challenge.getTestCases());
        challenge.setBookmark(challengeDTO.isBookmark());

        return challengeRepository.save(challenge);
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

        if(status != null) criteria.and("status").is(status);
        if(skill != null) criteria.and("skill").is(skill);
        if(difficulty != null) criteria.and("difficulty").is(difficulty);
        if(subdomain != null) criteria.and("subDomain").is(subdomain);

        Query query = new Query(criteria);

        return mongoTemplate.findAll(Challenge.class, query.toString());
    }
}
