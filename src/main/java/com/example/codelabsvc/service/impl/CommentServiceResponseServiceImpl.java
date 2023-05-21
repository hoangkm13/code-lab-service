package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.dto.SaveCommentResponseRequestDTO;

import com.example.codelabsvc.dto.UpdateCommentResponseDTO;
import com.example.codelabsvc.entity.CommentResponse;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.CommentResponseRepository;
import com.example.codelabsvc.service.CommentResponseService;

import com.example.codelabsvc.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class CommentServiceResponseServiceImpl implements CommentResponseService {
    private final CommentResponseRepository commentResponseRepository;
    private final UserService userService;

    public CommentServiceResponseServiceImpl(CommentResponseRepository commentResponseRepository, UserService userService) {
        this.commentResponseRepository = commentResponseRepository;
        this.userService = userService;
    }

    @Override
    public CommentResponse saveCommentResponse(SaveCommentResponseRequestDTO dto) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userService.findByUsername(authentication.getUsername());
        return commentResponseRepository.save(new CommentResponse(dto.getCommentId(), user, dto.getText()));
    }

    @Override
    public CommentResponse updateCommentResponse(UpdateCommentResponseDTO dto) {
        CommentResponse commentResponse = commentResponseRepository.findCommentResponseById(dto.getId());
        commentResponse.setText(dto.getText());
        commentResponse.setUpdatedAt(new Date().toString());
        return commentResponseRepository.save(commentResponse);
    }

    @Override
    public void deleteCommentResponse(String id) {
        commentResponseRepository.deleteCommentResponseById(id);
    }

    @Override
    public Page<CommentResponse> getAllByCommentId(String id, int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentResponseRepository.findAllByCommentId(id,pageable);
    }

}
