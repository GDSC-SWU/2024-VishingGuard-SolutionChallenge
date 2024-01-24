package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
public class PostResDto {
    private String id; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String nickname; // 작성자 정보
    private String updated_at;
    private int comment_count;
    private int heart_count;
    private Boolean isMyPost;


    @Builder
    public PostResDto(Post post, Boolean isMyPost){
        this.id=post.getId();
        this.content=post.getContent();
        this.title=post.getTitle();
        this.nickname=post.getUser().getNickname();
        this.updated_at = formatTimestamp(post.getUpdated_at());
        this.isMyPost=isMyPost;
    }

    private String formatTimestamp(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }

}
