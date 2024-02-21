package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateReqDto {
    @Schema(description = "updated Comment content")
    private String content;

    @Builder
    public CommentUpdateReqDto(String content){
        this.content=content;
    }
}
