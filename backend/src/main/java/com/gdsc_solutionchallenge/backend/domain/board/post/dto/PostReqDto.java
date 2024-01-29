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
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "게시글 내용")
    private String content;

    @Builder
    public PostReqDto(String title, String content){
        this.title=title;
        this.content=content;

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

    // 게시글 제목 관련 설정 메서드
    // 제목이 공백이거나 50자 이상을 초과할 경우 BaseException 발생됨
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "게시판 제목은 공백일 수 없습니다.");
        }
        this.title = title;
    }

    // 게시글 내용 관련 설정 메서드
    // 내용이 공백일 경우 BaseException 발생됨
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "게시판 내용은 공백일 수 없습니다.");
        }
        this.content = content;
    }
}
