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
@Tag(name = "Board API", description = "Board API Collection")
public class PostController {
    private final PostService postService;

    @PostMapping("/{userId}/create")
    @Operation(summary = "Create Post", description = "API to create a new post")
    public ResponseEntity<Object> save(@PathVariable("userId") Long userId,
                                       @RequestBody PostReqDto postReqDto){
        try {
            PostResDto postResDto = postService.savePost(userId, postReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Post created successfully", postResDto));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }

    @PatchMapping("/{userId}/{postId}/update")
    @Operation(summary = "Update Specific Post", description = "API to update a specific post")
    public ResponseEntity<Object> update(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId,
                                         @RequestBody PostUpdateReqDto postUpdateReqDto) throws Exception {
        try {
            PostResDto updatedPost = postService.updatePost(userId, postId, postUpdateReqDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Post updated successfully", updatedPost));
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
    @Operation(summary = "Get Specific Post", description = "API to get details of a specific post")
    public ResponseEntity<Object> findById (@PathVariable("userId") Long userId,
                                            @PathVariable("postId") String postId){
        try {
            PostResDto postResDto = postService.findPostById(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Post details retrieved successfully", postResDto ));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }

    @GetMapping("/read")
    @Operation(summary = "Get All Posts", description = "API to get a list of all posts")
    public ResponseEntity<Object> readAllPosts() {
        try {
            List<PostListResDto> posts = postService.getAllPosts();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "All posts retrieved successfully", posts));
        } catch (BaseException e) {
            return ResponseEntity
                    .status(e.getCode())
                    .body(new BaseErrorResponse(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }

    // Delete a specific post
    @DeleteMapping("/{userId}/{postId}/delete")
    @Operation(summary = "Delete Specific Post", description = "API to delete a specific post")
    public ResponseEntity<Object> delete(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") String postId){
        try {
            String post_Id = postService.deletePost(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(), "Post deleted successfully", post_Id));
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
