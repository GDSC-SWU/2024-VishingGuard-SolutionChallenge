package com.gdsc_solutionchallenge.backend.domain.board.post.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "게시판 API", description = "게시판 API 모음")
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    @Operation(summary = "게시글 생성", description = "게시글을 API")
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
    @Operation(summary = "게시글 수정", description = "게시글을 수정 API")
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
//
//    // 특정 id 게시글 조회
//    @GetMapping("/{id}")
//    @Operation(summary = "특정 게시글 조회", description = "조회할 게시글 id")
//
//    public ResponseEntity<Object> findById (@PathVariable Long id){
//        return boardService.findById(id);
//    }
//
//    // 게시글 목록 조회
//    @GetMapping("/")
//    @Operation(summary = "특정 게시글 조회", description = "조회할 게시글 id")
//
//    public List<BoardListDto> findAllDesc() {
//        return boardService.findAllByOrderByIdDesc();
//    }
//
//    // 특정 게시글 삭제
//    @DeleteMapping("/{id}")
//    @Operation(summary = "게시글 생성", description = "생성할 게시글 Dto")
//
//    public Long delete(@PathVariable Long id){
//        boardService.deletePost(id);
//        return id;
//    }
}