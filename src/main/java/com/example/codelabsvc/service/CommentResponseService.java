package com.example.codelabsvc.service;


import com.example.codelabsvc.dto.SaveCommentResponseRequestDTO;


import com.example.codelabsvc.dto.UpdateCommentResponseDTO;
import com.example.codelabsvc.entity.CommentResponse;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.data.domain.Page;


public interface CommentResponseService {
    CommentResponse saveCommentResponse(SaveCommentResponseRequestDTO dto) throws CustomException;
    CommentResponse updateCommentResponse(UpdateCommentResponseDTO dto);
    void deleteCommentResponse(String id);
    Page<CommentResponse> getAllByCommentId(String id, int page,int size);
}
