package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String commentId;
    private String postId;
    private Long userId;
    private String username;
    private String content;
    private String updated_at;
    private String created_at;
    private String profile_image;
    private boolean isAuthorComment;

    @Builder
    public CommentResDto(Comment comment, Post post){
        this.commentId=comment.getId();
        this.postId=comment.getPost_id();
        this.userId=comment.getUser_id();
        this.content=comment.getContent();
        this.username=comment.getUser().getUsername();
        this.updated_at = formatTimestamp(comment.getUpdated_at());
        this.created_at = formatTimestamp(comment.getCreated_at());
        this.isAuthorComment=comment.getUser_id().equals(post.getUser_id());
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
