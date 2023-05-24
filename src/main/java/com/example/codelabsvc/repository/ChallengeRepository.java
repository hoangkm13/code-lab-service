package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Challenge;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeRepository extends MongoRepository<Challenge, String> {

    boolean existsByName(String name);

    List<Challenge> findAllByBookmark(boolean bookmarkStatus);

}
