package com.example.codelabsvc.controller;

import com.example.codelabsvc.dto.CommentResponseDTO;
import com.example.codelabsvc.dto.SaveChildCommentRequestDTO;
import com.example.codelabsvc.dto.SaveCommentRequestDTO;
import com.example.codelabsvc.dto.UpdateCommentDTO;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{challengeId}")
    public ApiResponse<Page<CommentResponseDTO>> getCommentByChallengeId(@PathVariable String challengeId,
                                                                         @RequestParam(defaultValue = "0", required = false) int page,
                                                                         @RequestParam(defaultValue = "5", required = false) int size) {
        Page<CommentResponseDTO> results = commentService.getAllByChallengeId(challengeId, page, size);
        return ApiResponse.successWithResult(results);
    }

    @PostMapping
    public ApiResponse<CommentResponseDTO> saveComment(@RequestBody @Valid SaveCommentRequestDTO dto) {
        CommentResponseDTO comment = commentService.saveComment(dto);
        return ApiResponse.successWithResult(comment);
    }

    @PutMapping
    public ApiResponse<CommentResponseDTO> updateComment(@RequestBody @Valid UpdateCommentDTO dto) throws CustomException {
        CommentResponseDTO comment = commentService.updateComment(dto);
        return ApiResponse.successWithResult(comment);
    }

    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
    }

    @PostMapping("reply-comment")
    public ApiResponse<CommentResponseDTO> replyComment(@RequestBody @Valid SaveChildCommentRequestDTO dto) {
        CommentResponseDTO comment = commentService.replyComment(dto);
        return ApiResponse.successWithResult(comment);
    }
}
