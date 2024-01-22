package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.google.cloud.Timestamp;
import lombok.Getter;

@Getter
public class PostReadResDto {
    private String id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String nickname; // 작성자 정보
    private Timestamp updated_at;
//    private int comment_count;
//    private int heart_count;

    // BoardReadDto 의 생성자
    public PostReadResDto(Post post){
        this.id=post.getId();
        this.content=post.getContent();
        this.title=post.getTitle();
        this.nickname=post.getUser().getNickname();
        this.updated_at=post.getUpdated_at();
    }
}
