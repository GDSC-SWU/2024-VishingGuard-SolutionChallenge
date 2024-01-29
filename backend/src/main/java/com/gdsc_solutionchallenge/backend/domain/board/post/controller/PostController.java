package com.gdsc_solutionchallenge.backend.domain.board.post.controller;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.service.PostService;
import com.gdsc_solutionchallenge.backend.global.common.BaseResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseErrorResponse;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "게시판 API", description = "게시판 API 모음")
public class PostController {
    private final PostService postService;

    @PostMapping("/{userId}/create")
    @Operation(summary = "게시글 등록", description = "게시글 등록 API")
    public ResponseEntity<Object> save(@PathVariable("userId") Long userId,
                                       @RequestBody PostReqDto postReqDto){
        try {
            PostResDto postResDto = postService.savePost(userId, postReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "게시글 작성 완료", postResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
        }
    }

    @PatchMapping("/{userId}/{postId}/update")
    @Operation(summary = "특정 게시글 수정", description = "특정 게시글 수정 API")
    public ResponseEntity<Object> update(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId,
                                         @RequestBody PostUpdateReqDto postUpdateReqDto) throws Exception {
        try {
            PostResDto updatedPost = postService.updatePost(userId, postId, postUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
        .body(new BaseResponse<>(HttpStatus.OK.value(), "게시글 수정 완료", updatedPost));
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

    @GetMapping("/{userId}/{postId}/read")
    @Operation(summary = "특정 게시글 조회", description = "특정 게시글 조회 API")
    public ResponseEntity<Object> findById (@PathVariable("userId") Long userId,
                                            @PathVariable("postId") String postId){
        try {
            PostResDto postResDto = postService.findPostById(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "특정 게시글 조회 완료", postResDto ));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"+e.getMessage()));
        }
    }

    @GetMapping("/read")
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 API")
    public ResponseEntity<Object> readAllPosts() {
        try {
            List<PostListResDto> posts = postService.getAllPosts();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "게시글 목록 조회 완료", posts));
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

    // 특정 게시글 삭제
    @DeleteMapping("/{userId}/{postId}/delete")
    @Operation(summary = "특정 게시글 삭제", description = "특정 게시글 삭제 API")

    public ResponseEntity<Object> delete(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId){
        try {
            String post_Id = postService.deletePost(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "특정 게시글 삭제 완료", post_Id));
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
}
