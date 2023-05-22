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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;


    @Override
    public Challenge addChallenge(ChallengeDTO challengeDTO) {
        Challenge challenge = new Challenge();
        return challengeRepository.save(challengeDTO.toChallenge(challenge));
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
//        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
//        List<Future<TestCase>> futureList = new ArrayList<>();
//
//        if (challenge.isPresent()) {
//
//            File folder = new File(challenge
//                    .get()
//                    .getName()
//                    .replace(" ", "-")
//                    .toLowerCase()
//                    + "-test-case");
//
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
//
//            List<TestCase> testCases = challenge.get().getTestCases();
//            ExecutorService executorService = Executors.newFixedThreadPool(testCases.size());
//
//            Callable<TestCase> callable;
//            Future<TestCase> future;
//
//            for (int i = 0; i < testCases.size(); i++) {
//                callable = new ExecutionFactory(i, preScript, testCases.get(i), challenge.get().getName());
//                future = executorService.submit(callable);
//
//                futureList.add(future);
//            }
//
//            executorService.shutdown();
//
//            for (Future<TestCase> f : futureList) {
//                try {
//                    System.out.println(f.get());
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return null;
    }

    @Override
    public List<TestCase> submitCode(MultipartFile inputFile, MultipartFile output, MultipartFile sourceCode) {
        List<Future<TestCase>> futureList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<TestCase> callable;
        Future<TestCase> future;

        callable = new ExecutionFactory(inputFile, output, sourceCode);
        future = executorService.submit(callable);

        futureList.add(future);

        executorService.shutdown();

        for (Future<TestCase> f : futureList) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
