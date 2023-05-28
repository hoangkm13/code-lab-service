package com.example.codelabsvc.controller;

import com.example.codelabsvc.controller.response.comment.CommentResponseDTO;
import com.example.codelabsvc.controller.response.comment.SaveChildCommentRequestDTO;
import com.example.codelabsvc.controller.response.comment.SaveCommentRequestDTO;
import com.example.codelabsvc.controller.response.comment.UpdateCommentDTO;
import com.example.codelabsvc.entity.Comment;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public void deleteComment(@PathVariable String id) throws CustomException {
        commentService.deleteComment(id);
    }

    @PostMapping("reply-comment")
    public ApiResponse<CommentResponseDTO> replyComment(@RequestBody @Valid SaveChildCommentRequestDTO dto) throws CustomException {
        CommentResponseDTO comment = commentService.replyComment(dto);
        return ApiResponse.successWithResult(comment);
    }

    @GetMapping("get-comment-replies/{parentCommentId}")
    public ApiResponse<List<Comment>> getListReplies(@PathVariable String parentCommentId) throws CustomException {
        var listReplies = commentService.getListReplies(parentCommentId);
        return ApiResponse.successWithResult(listReplies);
    }
}
