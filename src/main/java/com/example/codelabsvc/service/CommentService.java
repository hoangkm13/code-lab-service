package com.example.codelabsvc.service;

import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;
import com.example.codelabsvc.entity.Comment;

import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;


public interface CommentService {
     Comment saveComment(SaveCommentRequestDTO dto) throws CustomException;
     Comment updateComment(UpdateCommentDTO dto);
     void deleteComment(String id);
     Page<Comment> getAllByChallengeId(String id , int page,int size);
}
