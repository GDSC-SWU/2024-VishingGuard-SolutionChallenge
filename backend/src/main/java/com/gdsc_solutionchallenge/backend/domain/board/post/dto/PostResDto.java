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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class PostResDto {
    private String postId; // 게시글 ID
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String nickname; // 작성자 정보
    private String userId; // 작성자 정보
    private String updated_at;
    private String created_at;
    private int comment_count;
    private int heart_count;
    private boolean isMyPost;

    @Builder
    public PostResDto(Post post, boolean isMyPost ,int comment_count,int heart_count) throws Exception {
        this.postId=post.getId();
        this.content=post.getContent();
        this.title=post.getTitle();
        this.nickname=post.getUser().getNickname();
        this.userId=post.getUser_id();
        this.updated_at = formatTimestamp(post.getUpdated_at());
        this.created_at = formatTimestamp(post.getCreated_at());
        // 댓글 수 설정
        this.comment_count = comment_count;

        // 좋아요 수 설정
        this.heart_count = heart_count;
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
