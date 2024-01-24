package com.gdsc_solutionchallenge.backend.domain.board.comment.controller;

import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.service.CommentService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Tag(name = "댓글 API", description = "댓글 API 모음")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{user-id}/{post-id}/comments")
    @Operation(summary = "댓글 등록", description = "댓글 등록 API")
    public ResponseEntity<Object> save(@PathVariable("userId") String userId,
                                       @PathVariable("postId") String postId,
                                       @RequestBody CommentReqDto commentReqDto) {
        try {
            CommentResDto commentResDto = commentService.saveComment(userId, postId, commentReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "댓글 작성 완료", commentResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"));
        }
    }


//    @GetMapping("/{memberId}/{post-id}/comments")
//    @Operation(summary = "댓글 조회", description = "댓글 조회 API")
//    public ResponseEntity<Object> read(@PathVariable("post-id") Long postId) {
//        ReadCommentResDto readCommentResDto = commentService.readComment(postId);
//
//        return ResponseEntity.ok(DataResponseDto.of(readCommentResDto, 200));
//    }
//
//    @DeleteMapping("/{memberId}/{post-id}/comments/{comment-id}")
//    @Operation(summary = "댓글 삭제", description = "댓글 삭제 API")
//    public ResponseEntity<Object> delete(/*@AuthenticationPrincipal UserEntity user, */@RequestParam("temp") Long userId, @PathVariable("comment-id") Long commentId) {
//        commentService.deleteComment(null, userId, commentId);
//
//        return ResponseEntity.ok(ResponseDto.of(200));
//    }

    //    @PatchMapping("/{memberId}/{post-id}/comments/{comment-id}")
//    @Operation(summary = "댓글 수정", description = "댓글 수정 API")
//    public ResponseEntity<Object> update(/*@AuthenticationPrincipal UserEntity user, */@RequestParam("temp") Long userId, @PathVariable("comment-id") Long commentId, @RequestBody @Valid NewCommentReqDto newCommentReqDto) {
//        UpdateCommentResDto updateCommentResDto = commentService.updateComment(null, userId, commentId, newCommentReqDto);
//
//        return ResponseEntity.status(201).body(DataResponseDto.of(updateCommentResDto, 201));
//    }
//

}
