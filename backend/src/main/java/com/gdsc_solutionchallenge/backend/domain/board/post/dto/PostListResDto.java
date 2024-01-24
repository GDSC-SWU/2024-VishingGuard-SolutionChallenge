package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.google.cloud.Timestamp;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class PostListResDto {
    private String id; // 게시글 ID
    private String title; // 게시글 제목
    private String nickname; // 작성자 닉네임
    private String updated_at;
    private String contentSnippet;
    private int comment_count;
    private int heart_count;

    // BoardListDto 의 생성자
    public PostListResDto(Post post){
        this.id=post.getId();
        this.title=post.getTitle();
        this.nickname=post.getUser().getNickname(); // UserEntity 에서 nickname 가져오기
        this.updated_at = formatTimestamp(post.getUpdated_at().toDate().toInstant());
        this.contentSnippet=getContentSnippet(post.getContent(), 82);
    }

    private String getContentSnippet(String content, int maxChars) {
        if (content.length() <= maxChars) {
            return content;
        } else {
            return content.substring(0, maxChars);
        }
    }

    private String formatTimestamp(Instant instant) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Date.from(instant);
        return sdf.format(date);
    }
}
