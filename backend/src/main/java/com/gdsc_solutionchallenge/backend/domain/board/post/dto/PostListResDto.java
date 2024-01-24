package com.gdsc_solutionchallenge.backend.domain.board.post.dto;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.CommentRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.google.cloud.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostListResDto {
    private String postId; // 게시글 ID
    private String title; // 게시글 제목
    private String nickname; // 작성자 닉네임
    private String updated_at;
    private String contentSnippet;
    private int comment_count;
    private int heart_count;

    @Builder
    public PostListResDto(Post post, CommentRepository commentRepository, HeartRepository heartRepository) throws Exception {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.updated_at = formatTimestamp(post.getUpdated_at().toInstant());
        this.contentSnippet = getContentSnippet(post.getContent(), 82);

        // 댓글 수와 좋아요 수를 설정
        this.comment_count = commentRepository.getAllCommentByPostId(post.getId()).size();
        this.heart_count = heartRepository.getAllHeartByPostId(post.getId()).size();
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

    public static List<PostListResDto> convertToDtoList(List<Post> posts, CommentRepository commentRepository, HeartRepository heartRepository) {
        return posts.stream()
                .map(post -> {
                    try {
                        return new PostListResDto(post, commentRepository, heartRepository);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
