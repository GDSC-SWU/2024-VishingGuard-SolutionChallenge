package com.gdsc_solutionchallenge.backend.domain.board.comment.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String commentId;
    private String postId;
    private String nickname;
    private String content;
    private String updated_at;
    private String profile_image;
    private Boolean isAuthorComment;

    @Builder
    public CommentResDto(Comment comment, String nickName){
        this.commentId=comment.getId();
        this.postId=comment.getPost_id();
        this.content=comment.getContent();
        this.nickname=comment.getUser().getNickname();
        this.updated_at = formatTimestamp(comment.getUpdated_at());
        this.isAuthorComment=comment.getUser().getNickname().equals(nickName);
    }

    private String formatTimestamp(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }

}
