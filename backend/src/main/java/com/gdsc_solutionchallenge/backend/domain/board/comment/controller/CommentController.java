package com.gdsc_solutionchallenge.backend.domain.board.comment.controller;

import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.service.CommentService;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment API", description = "Collection of Comment APIs")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/{postId}/create")
    @Operation(summary = "Create Comment", description = "API to create a comment")
    public ResponseEntity<Object> save(@PathVariable("userId") Long userId,
                                       @PathVariable("postId") String postId,
                                       @RequestBody CommentReqDto commentReqDto) {
        try {
            CommentResDto commentResDto = commentService.saveComment(userId, postId, commentReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Comment created successfully", commentResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

    @GetMapping("/{userId}/{postId}/read")
    @Operation(summary = "Read Comments", description = "API to read comments")
    public ResponseEntity<Object> read(@PathVariable("userId") Long userId,
                                       @PathVariable("postId") String postId) {
        try {
            List<CommentResDto> commentList = commentService.getAllComments(userId,postId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Comment list retrieved successfully", commentList));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

    @DeleteMapping("/{userId}/{postId}/{commentId}/delete")
    @Operation(summary = "Delete Comment", description = "API to delete a comment")
    public ResponseEntity<Object> delete(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId,
                                         @PathVariable("commentId") String commentId) {

        try {
            String comment_id = commentService.deleteComment(userId, postId, commentId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Comment deleted successfully", comment_id));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }

    @PatchMapping("/{userId}/{postId}/{commentId}/update")
    @Operation(summary = "Update Comment", description = "API to update a comment")
    public ResponseEntity<Object> update(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId,
                                         @PathVariable("commentId") String commentId,
                                         @RequestBody CommentUpdateReqDto commentUpdateReqDto) {
        try {
            CommentResDto comment = commentService.updateComment(userId, postId, commentId, commentUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Comment updated successfully", comment));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"));
        }
    }
}
