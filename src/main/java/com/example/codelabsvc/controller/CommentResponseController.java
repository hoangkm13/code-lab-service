package com.example.codelabsvc.controller;

import com.example.codelabsvc.dto.SaveCommentResponseRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentResponseDTO;
import com.example.codelabsvc.entity.CommentResponse;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.service.CommentResponseService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentResponseController {
    private final CommentResponseService commentResponseService;

    public CommentResponseController(CommentResponseService commentResponseService) {
        this.commentResponseService = commentResponseService;
    }

    @GetMapping("comment-response/{commentId}")
    public Page<CommentResponse> getCommentResponseByCommentId(@PathVariable String commentId,
                                                               @RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "5", required = false) int size) {
        return commentResponseService.getAllByCommentId(commentId, page, size);
    }

    @PostMapping("comment-response")
    public CommentResponse saveCommentResponse(@RequestBody @Valid SaveCommentResponseRequestDTO dto) throws CustomException {
        return commentResponseService.saveCommentResponse(dto);
    }

    @PostMapping("update-comment-response")
    public CommentResponse updateCommentResponse(@RequestBody @Valid UpdateCommentResponseDTO dto) {
        return commentResponseService.updateCommentResponse(dto);
    }

    @DeleteMapping("delete-comment-response/{id}")
    public void deleteCommentResponse(@PathVariable String id) {
        commentResponseService.deleteCommentResponse(id);
    }
}
