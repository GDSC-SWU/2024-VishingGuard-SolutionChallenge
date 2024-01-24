package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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

//    public Comment toEntity(User user, Post post){
//        Comment build = Comment.builder()
//                .user(user)
//                .post(post)
//                .content(content)
//                .build();
//        return build;
//    }
}
