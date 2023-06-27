package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Challenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChallengeRepository extends MongoRepository<Challenge, String> {

    boolean existsByName(String name);

    @Query("{'_id' : { $in : ?0 } }")
    List<Challenge> findChallengesByChallengeIds(List<String> challengeIds);

    @Query("{'name': {'$regex': ?0, '$options': 'i'}}")
    List<Challenge> searchChallengeByNameLike(String challengeName);

}
