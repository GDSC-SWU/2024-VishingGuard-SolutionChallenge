package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateReqDto {
    @Schema(description = "수정할 게시글 제목")
    private String title;
    @Schema(description = "수정할 게시글 내용")
    private String content;

    @Builder
    public PostUpdateReqDto(String title, String content){
        this.title=title;
        this.content=content;
    }
}