package com.gdsc_solutionchallenge.backend.domain.board.post.controller;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReadResDto;
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

    @PostMapping("/")
    @Operation(summary = "게시글 생성", description = "게시글 등록 API")
    public ResponseEntity<Object> save(@RequestBody PostReqDto postReqDto){
        try {
            String postId = postService.savePost(postReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "게시글 작성 완료", postId));
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

    @PutMapping("/{id}")
    @Operation(summary = "특정 게시글 수정", description = "특정 게시글 수정 API")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody PostUpdateReqDto postUpdateReqDto) throws Exception {
        try {
            String postId = postService.updatePost(id, postUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "게시글 수정 완료", postId ));
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

    @GetMapping("/{id}")
    @Operation(summary = "특정 게시글 조회", description = "특정 게시글 조회 API")
    public ResponseEntity<Object> findById (@PathVariable("id") String id){
        try {
            Post post = postService.findPostById(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "특정 게시글 조회 완료", new PostReadResDto(post) ));
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

    @GetMapping("/")
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 API")
    public ResponseEntity<Object> findAllPosts() {
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
    @DeleteMapping("/{id}")
    @Operation(summary = "특정 게시글 삭제", description = "특정 게시글 삭제 API")

    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        try {
            String postId = postService.deletePost(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "특정 게시글 삭제 완료", postId));
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
