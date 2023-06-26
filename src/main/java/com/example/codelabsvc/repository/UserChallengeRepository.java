package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.UserChallenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChallengeRepository extends MongoRepository<UserChallenge, String> {
    @Query("{'userId' :  ?0, 'challengeId': {$in : ?1}, 'status': 'SOLVED' }")
    List<UserChallenge> findSolvedUserChallenges(String userId, List<String> challengeId);

    boolean existsByUserIdAndChallengeId(String userId, String challengeId);

}
