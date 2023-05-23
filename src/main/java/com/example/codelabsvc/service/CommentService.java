package com.example.codelabsvc.service;

import com.example.codelabsvc.dto.CommentResponseDTO;
import com.example.codelabsvc.dto.SaveChildCommentRequestDTO;
import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;

import org.springframework.data.domain.Page;


public interface CommentService {
     CommentResponseDTO saveComment(SaveCommentRequestDTO dto) ;
     CommentResponseDTO updateComment(UpdateCommentDTO dto);
     void deleteComment(String id);
     Page<CommentResponseDTO> getAllByChallengeId(String id , int page, int size);
     CommentResponseDTO replyComment(SaveChildCommentRequestDTO dto) ;
}
