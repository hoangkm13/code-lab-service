package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment findCommentById(String id);

    void deleteCommentById(String id);

    Page<Comment> getAllByChallengeId(String id, Pageable pageable);

    @Query("{'_id' : { $in : ?0 } }")
    List<Comment> findCommentsByChildCommentIds(List<String> childCommentIds);
}
