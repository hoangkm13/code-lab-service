package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.request.topic.TopicDTO;
import com.example.codelabsvc.controller.response.topic.ListTopicsPercentResponse;
import com.example.codelabsvc.entity.*;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.ChallengeRepository;
import com.example.codelabsvc.repository.TopicRepository;
import com.example.codelabsvc.repository.UserChallengeRepository;
import com.example.codelabsvc.repository.UserTopicRepository;
import com.example.codelabsvc.service.TopicService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserTopicRepository userTopicRepository;

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public UserTopic getUserTopic(String id, String userId) throws CustomException {

        var topic = topicRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TOPIC_NOT_EXIST));

        int totalPoints = 0;
        int userPoints = 0;

        if (CollectionUtils.isNotEmpty(topic.getChallengeIds())) {
            var listChallenges = this.challengeRepository.findChallengesByChallengeIds(topic.getChallengeIds());
            if (listChallenges.size() != topic.getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }

            for (Challenge challenge : listChallenges) {
                totalPoints = totalPoints + challenge.getPoints();
            }

            topic.setTotalPoints(totalPoints);

            this.topicRepository.save(topic);

            var userChallenges = this.userChallengeRepository.findSolvedUserChallenges(userId, topic.getChallengeIds());
            List<String> userChallengeId = new ArrayList<>();
            for (UserChallenge challenge : userChallenges) {
                userChallengeId.add(challenge.getChallengeId());
            }

            var challenges = this.challengeRepository.findChallengesByChallengeIds(userChallengeId);

            for (Challenge challenge : challenges) {
                userPoints = userPoints + challenge.getPoints();
            }

        }

        var userTopic = this.userTopicRepository.findUserTopicByUserIdAndTopicId(userId, id);
        userTopic.setTotalPoints(totalPoints);
        userTopic.setUserPoints(userPoints);
        this.userTopicRepository.save(userTopic);

        return userTopic;
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

        if (CollectionUtils.isNotEmpty(topicDTO.getChallengeIds())) {
            var listChallenges = this.challengeRepository.findChallengesByChallengeIds(topicDTO.getChallengeIds());
            if (listChallenges.size() != topicDTO.getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }

            topic.setChallengeIds(topicDTO.getChallengeIds());
        }

        topic.setCreatedBy(authentication.getUsername());
        topic.setCreatedAt(LocalDate.now().toString());
        UserTopic userTopic = new UserTopic();
        userTopic.setUserId(authentication.getId());
        userTopic.setTopicId(topic.getId());
        this.userTopicRepository.save(userTopic);

        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(TopicDTO topicDTO) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Topic topic = getTopicById(topicDTO.getId());

        topic.setName(topicDTO.getName() != null ? topicDTO.getName() : topic.getName());
        topic.setDescription(topicDTO.getDescription() != null ? topicDTO.getDescription() : topic.getDescription());

        if (CollectionUtils.isNotEmpty(topicDTO.getChallengeIds())) {
            var listChallenges = this.challengeRepository.findChallengesByChallengeIds(topicDTO.getChallengeIds());
            if (listChallenges.size() != topicDTO.getChallengeIds().size()) {
                throw new CustomException(ErrorCode.CHALLENGE_NOT_EXISTED_OR_INVALID);
            }

            for (String newChallengeId : topicDTO.getChallengeIds()) {
                if (!topic.getChallengeIds().contains(newChallengeId)) {
                    topic.getChallengeIds().add(newChallengeId);
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

    @Override
    public List<UserTopic> ranking(String topicId) {
        var allUserTopic = this.userTopicRepository.findUserTopicByTopicId(topicId);

        List<UserTopic> rankedList = allUserTopic.stream().sorted(Comparator.comparingInt(UserTopic::getUserPoints).reversed()).collect(Collectors.toList());

        return rankedList;
    }

    @Override
    public List<ListTopicsPercentResponse> getAllTopicWithPoint() throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        List<ListTopicsPercentResponse> listTopicsPercentRespons = new ArrayList<>();

        List<UserTopic> userTopics = userTopicRepository.findAllByUserId(authentication.getId())
                .stream()
                .sorted(Comparator.comparing(UserTopic::getId))
                .collect(Collectors.toList());

        List<Topic> topics = topicRepository.findAllById(userTopics
                .stream().map(UserTopic::getTopicId).collect(Collectors.toList()));

        if(topics.size() != userTopics.size()){
            throw new CustomException(ErrorCode.TOPIC_NOT_EXIST);
        }

        for (int i = 0; i < userTopics.size(); i++) {
            listTopicsPercentRespons.add(new ListTopicsPercentResponse(topics.get(i),
                    userTopics.get(i).getUserPoints() * 100 / userTopics.get(i).getTotalPoints()));
        }

        return listTopicsPercentRespons;
    }
}
