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

    // 게시글 제목 관련 설정 메서드
    // 제목이 공백이거나 50자 이상을 초과할 경우 BaseException 발생됨
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "게시판 제목은 공백일 수 없습니다.");
        }
        if (title.length() > 50) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(),"게시판 제목은 " + 50 + "자 이하로 입력해야 합니다.");
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