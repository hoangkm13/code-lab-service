package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    Comment findCommentById(String id);
    void deleteCommentById(String id);
    Page<Comment> getAllByChallengeId(String id, Pageable pageable);
}
