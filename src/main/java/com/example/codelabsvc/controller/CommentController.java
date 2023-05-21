package com.example.codelabsvc.controller;

import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;
import com.example.codelabsvc.entity.Comment;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("comment/{challengeId}")
    public Page<Comment> getCommentByChallengeId(@PathVariable String challengeId,
                                                 @RequestParam(defaultValue = "0",required = false) int page,
                                                 @RequestParam(defaultValue = "5",required = false) int size) {
        return commentService.getAllByChallengeId(challengeId,page,size);
    }

    @PostMapping("comment")
    public Comment saveComment(@RequestBody @Valid SaveCommentRequestDTO dto) throws CustomException {
        return commentService.saveComment(dto);
    }

    @PostMapping("update-comment")
    public Comment updateComment(@RequestBody @Valid UpdateCommentDTO dto) {
        return commentService.updateComment(dto);
    }

    @DeleteMapping("delete-comment/{id}")
    public void deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
