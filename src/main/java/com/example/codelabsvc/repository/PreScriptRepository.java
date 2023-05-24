package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.PreScript;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreScriptRepository extends MongoRepository<PreScript, String> {
    List<PreScript> findAllByChallengeId(String challengeId);
}
