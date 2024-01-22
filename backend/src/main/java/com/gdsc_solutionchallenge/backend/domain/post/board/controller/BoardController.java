package com.gdsc_solutionchallenge.backend.domain.post.board.controller;

import com.gdsc_solutionchallenge.backend.domain.post.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
@Tag(name = "게시판 API", description = "게시판 API 모음")
public class BoardController {
    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/")
    @Operation(summary = "게시글 생성", description = "생성할 게시글 Dto")
    public ResponseEntity<Object> save(@RequestBody BoardDto boardDto){
        return boardService.savePost(boardDto);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "수정할 게시글 id + 수정할 게시글 Dto")

    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody BoardUpdateDto requestDto){
        return boardService.updatePost(id, requestDto);
    }

    // 특정 id 게시글 조회
    @GetMapping("/{id}")
    @Operation(summary = "특정 게시글 조회", description = "조회할 게시글 id")

    public ResponseEntity<Object> findById (@PathVariable Long id){
        return boardService.findById(id);
    }

    // 게시글 목록 조회
    @GetMapping("/")
    @Operation(summary = "특정 게시글 조회", description = "조회할 게시글 id")

    public List<BoardListDto> findAllDesc() {
        return boardService.findAllByOrderByIdDesc();
    }

    // 특정 게시글 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 생성", description = "생성할 게시글 Dto")

    public Long delete(@PathVariable Long id){
        boardService.deletePost(id);
        return id;
    }
}
