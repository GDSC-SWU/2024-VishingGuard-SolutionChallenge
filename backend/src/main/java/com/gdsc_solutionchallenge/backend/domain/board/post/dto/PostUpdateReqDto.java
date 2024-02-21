package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

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

    // Method for setting the post title
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Post title cannot be empty.");
        }
        this.title = title;
    }

    // Method for setting the post content
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Post content cannot be empty.");
        }
        this.content = content;
    }
}