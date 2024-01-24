package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
public class CommentResDto {
    private String commentId;
    private String postId;
    private String nickname;
    private String content;
    private String updated_at;
    private String profile_image;
    private Boolean isMyComment;

    @Builder
    public CommentResDto(Comment comment){
        this.commentId=comment.getId();
        this.content=comment.getContent();
        this.nickname=comment.getUser().getNickname();
        this.updated_at = formatTimestamp(comment.getUpdated_at().toDate().toInstant());
    }

    private String formatTimestamp(Instant instant) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Date.from(instant);
        return sdf.format(date);
    }
}
