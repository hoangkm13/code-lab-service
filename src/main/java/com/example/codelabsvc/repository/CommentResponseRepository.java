package com.example.codelabsvc.repository;

import com.example.codelabsvc.entity.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentResponseRepository extends MongoRepository<CommentResponse,String> {
    CommentResponse findCommentResponseById(String id);
    Page<CommentResponse> findAllByCommentId(String commentId, Pageable pageable);
    void deleteCommentResponseById(String id);
}
