package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.dto.CommentResponseDTO;
import com.example.codelabsvc.dto.SaveChildCommentRequestDTO;
import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;
import com.example.codelabsvc.entity.Comment;
import com.example.codelabsvc.entity.User;

import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.CommentRepository;
import com.example.codelabsvc.service.CommentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;

    }

    @Override
    public CommentResponseDTO saveComment(SaveCommentRequestDTO dto) {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Comment comment = new Comment(dto.getChallengeId(), authentication.getUsername(), dto.getText(), dto.getCode(), true, null);
        comment.setId(UUID.randomUUID().toString());
        commentRepository.save(comment);
        return new CommentResponseDTO(comment.getId(),
                comment.getUserName(),
                comment.getCreatedAt(),
                comment.getText(),
                comment.getCode());

    }

    @Override
    public CommentResponseDTO updateComment(UpdateCommentDTO dto) throws CustomException {
        Comment comment = commentRepository.findCommentById(dto.getId());
        if (comment == null) {
            throw new CustomException(ErrorCode.COMMENT_NOT_EXIST);
        }

        comment.setText(dto.getText());
        comment.setCode(dto.getCode());
        comment.setUpdatedAt(LocalDateTime.now().toString());
        commentRepository.save(comment);

        return new CommentResponseDTO(comment.getId(),
                comment.getUserName(),
                comment.getCreatedAt(),
                comment.getText(),
                comment.getCode());


    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteCommentById(id);
    }

    @Override
    public Page<CommentResponseDTO> getAllByChallengeId(String id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.getAllByChallengeId(id, pageable);
        return comments.map(comment -> {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(comment.getId());
            dto.setUsername(comment.getUserName());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setText(comment.getText());
            dto.setCode(comment.getCode());
            return dto;
        });
    }

    @Override
    public CommentResponseDTO replyComment(SaveChildCommentRequestDTO dto) {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Comment comment = commentRepository.findCommentById(dto.getParentId());
        if (!comment.isParent) {
            return null;
        } else {
            Comment childComment = new Comment(dto.getChallengeId(), authentication.getUsername(), dto.getText(), "", false, null);
            childComment.setId(UUID.randomUUID().toString());
            commentRepository.save(childComment);
            if (comment.getChildCommentId() == null) {
                List<String> idList = new ArrayList<>();
                idList.add(childComment.getId());
                comment.setChildCommentId(idList);
            } else {
                comment.getChildCommentId().add(childComment.getId());
            }
            commentRepository.save(comment);
            return new CommentResponseDTO(childComment.getId(),
                    childComment.getUserName(),
                    childComment.getCreatedAt(),
                    childComment.getText(),
                    childComment.getCode());
        }
    }


}
