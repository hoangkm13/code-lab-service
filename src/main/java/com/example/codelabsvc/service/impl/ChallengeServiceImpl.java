package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Challenge createChallenge(ChallengeDTO challengeDTO) {
        return challengeRepository.save(challengeDTO.toChallenge(new Challenge()));
    }

    @Override
    public List<Challenge> getAllChallengesByTopic(String topicId) {
        var topic = topicRepository.findById(topicId);

        return topic.map(Topic::getChallenges).orElse(null);
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

}
