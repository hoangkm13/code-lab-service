package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Challenge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChallengeRepository extends MongoRepository<Challenge, String> {


}
