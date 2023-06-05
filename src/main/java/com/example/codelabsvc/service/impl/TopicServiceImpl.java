package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.request.topic.TopicDTO;
import com.example.codelabsvc.entity.Topic;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.service.TopicService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopicById(String id) throws CustomException {
        return topicRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_EXIST));
    }

    @Override
    public Topic createTopic(TopicDTO topicDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String uuid = UUID.randomUUID().toString();

        Topic topic = new Topic();
        topic.setId(uuid);
        topic.setName(topicDTO.getName());
        topic.setDescription(topicDTO.getDescription());
        topic.setTotalPoints(topicDTO.getTotalPoints());

        if (CollectionUtils.isNotEmpty(topicDTO.getChallengeIds())) {
            var listChallenges = this.challengeRepository.findChallengesByChallengeIds(topicDTO.getChallengeIds());
            if (listChallenges.size() != topicDTO.getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }

            topic.setChallengeIds(topicDTO.getChallengeIds());
        }

        topic.setStarIds(topicDTO.getStarIds());

        topic.setCreatedBy(authentication.getUsername());
        topic.setCreatedAt(LocalDate.now().toString());

        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(TopicDTO topicDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Topic topic = getTopicById(topicDTO.getId());

        topic.setName(topicDTO.getName() != null ? topicDTO.getName() : topic.getName());
        topic.setDescription(topicDTO.getDescription() != null ? topicDTO.getDescription() : topic.getDescription());
        topic.setTotalPoints(topicDTO.getTotalPoints() != null ? topicDTO.getTotalPoints() : topic.getTotalPoints());
        topic.setStarIds(topicDTO.getStarIds() != null ? topicDTO.getStarIds() : topic.getStarIds());

        if (CollectionUtils.isNotEmpty(topicDTO.getChallengeIds())) {
            var listTestCase = this.challengeRepository.findChallengesByChallengeIds(topicDTO.getChallengeIds());
            if (listTestCase.size() != topicDTO.getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }

            for (String newTestCaseId : topicDTO.getChallengeIds()) {
                if (!topic.getChallengeIds().contains(newTestCaseId)) {
                    topic.getChallengeIds().add(newTestCaseId);
                }
            }
        }

        topic.setUpdateBy(authentication.getUsername());
        topic.setUpdatedAt(LocalDate.now().toString());

        return topicRepository.save(topic);
    }

    @Override
    public Topic deleteTopic(String id) throws CustomException {
        Topic topic = getTopicById(id);
        topicRepository.delete(topic);
        return topic;
    }
}
