package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.UserTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTopicRepository extends MongoRepository<UserTopic, String> {
    UserTopic findUserTopicByUserIdAndTopicId(String userId, String topicId);
    @Query("{'topicId' :  ?0}")
    List<UserTopic> findUserTopicByTopicId(String topicId);

    List<UserTopic> findAllByUserId(String userId);
}
