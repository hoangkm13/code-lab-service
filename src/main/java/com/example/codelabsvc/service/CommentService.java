package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.response.comment.CommentResponseDTO;
import com.example.codelabsvc.controller.response.comment.SaveChildCommentRequestDTO;
import com.example.codelabsvc.controller.response.comment.SaveCommentRequestDTO;
import com.example.codelabsvc.controller.response.comment.UpdateCommentDTO;

import com.example.codelabsvc.entity.Comment;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CommentService {
     CommentResponseDTO saveComment(SaveCommentRequestDTO dto) ;
     CommentResponseDTO updateComment(UpdateCommentDTO dto) throws CustomException;
     void deleteComment(String id) throws CustomException;
     Page<CommentResponseDTO> getAllByChallengeId(String id , int page, int size);
     CommentResponseDTO replyComment(SaveChildCommentRequestDTO dto) throws CustomException;

     List<Comment> getListReplies(String parentCommentId) throws CustomException;
}
