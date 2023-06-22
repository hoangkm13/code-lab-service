package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.UserChallenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChallengeRepository extends MongoRepository<UserChallenge, String> {
    List<UserChallenge> findAllByUserId(String userId);

    boolean existsByUserIdAndChallengeId(String userId, String challengeId);
    List<UserChallenge> findAllByUserIdAndChallengeIdIn(String userId, List<String> challengeId);
}
