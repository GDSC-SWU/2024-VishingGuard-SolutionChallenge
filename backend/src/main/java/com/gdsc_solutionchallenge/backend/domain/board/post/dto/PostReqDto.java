package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostReqDto {
    @Schema(description = "Post title")
    private String title;
    @Schema(description = "Post content")
    private String content;

    @Builder
    public PostReqDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post toEntity(User user){
        Post build = Post.builder()
                .user(user)
                .user_id(user.getId())
                .title(title)
                .content(content)
                .build();
        return build;
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
