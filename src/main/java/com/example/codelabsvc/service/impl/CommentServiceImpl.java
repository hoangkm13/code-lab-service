package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;
import com.example.codelabsvc.entity.Comment;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.CommentRepository;
import com.example.codelabsvc.service.CommentService;
import com.example.codelabsvc.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public Comment saveComment(SaveCommentRequestDTO dto) throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user = userService.findByUsername(authentication.getUsername());
        return commentRepository.save(new Comment(dto.getChallengeId(), user, dto.getText(), dto.getCode()));
    }

    @Override
    public Comment updateComment(UpdateCommentDTO dto) {
        Comment comment = commentRepository.findCommentById(dto.getId());
        comment.setText(dto.getText());
        comment.setCode(dto.getCode());
        comment.setUpdatedAt(new Date().toString());
        return commentRepository.save(comment);

    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteCommentById(id);
    }

    @Override
    public Page<Comment> getAllByChallengeId(String id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.getAllByChallengeId(id, pageable);
    }


}
