package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChallengeRepository extends MongoRepository<Challenge, String> {

    boolean existsByName(String name);

    List<Challenge> findAllByBookmark(boolean bookmarkStatus);

    @Query("{'_id' : { $in : ?0 } }")
    List<TestCase> findChallengesByChallengeIds(List<String> challengeIds);

}
