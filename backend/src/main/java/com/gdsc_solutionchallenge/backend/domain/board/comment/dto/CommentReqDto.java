package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentReqDto {
    @Schema(description = "댓글 내용")
    private String content;

    @Builder
    public CommentReqDto(String content){
        this.content=content;
    }

}
