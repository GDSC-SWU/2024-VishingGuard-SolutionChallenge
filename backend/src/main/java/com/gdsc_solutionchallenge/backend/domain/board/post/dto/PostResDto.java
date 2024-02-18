package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.CommentRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.google.protobuf.util.Timestamps;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class PostResDto {
    private String postId;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private String updated_at;
    private String created_at;
    private int comment_count;
    private int heart_count;
    private boolean isMyPost;

    @Builder
    public PostResDto(Post post, boolean isMyPost/* ,int comment_count,int heart_count*/) throws Exception {
        this.postId=post.getId();
        this.content=post.getContent();
        this.title=post.getTitle();
        this.username=post.getUser().getUsername();
        this.userId=post.getUser_id();
        this.updated_at = formatTimestamp(post.getUpdated_at());
        this.created_at = formatTimestamp(post.getCreated_at());
        this.comment_count = post.getComment_count();
        this.heart_count = post.getHeart_count();
        this.isMyPost=isMyPost;
    }

    private String formatTimestamp(Date date) {
        if (date != null) {
            Instant instant = date.toInstant();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")
                    .withZone(ZoneId.of("Asia/Seoul")); // 시간대 정보 추가
            return formatter.format(instant);
        }
        return null;
    }

}
