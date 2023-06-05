package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.BookmarkedChallenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkedChallengeRepository extends MongoRepository<BookmarkedChallenge, String> {
    BookmarkedChallenge findByUserIdAndChallengeId(String userId, String challengeId);

    List<BookmarkedChallenge> findAllByUserId(String userId);

    boolean existsByUserIdAndChallengeId(String userId, String challengeId);
}
