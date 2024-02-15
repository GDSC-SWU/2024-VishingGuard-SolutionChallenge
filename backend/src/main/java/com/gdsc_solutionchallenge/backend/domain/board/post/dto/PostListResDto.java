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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostListResDto {
    private String postId; // Post ID
    private String title; // Post title
    private String username; // Author's name
    private String updated_at;
    private String created_at;
    private String content;
    private int comment_count;
    private int heart_count;

    @Builder
    public PostListResDto(Post post
            /*, CommentRepository commentRepository, HeartRepository
            heartRepository*/) throws Exception {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.updated_at = formatTimestamp(post.getUpdated_at());
        this.created_at = formatTimestamp(post.getCreated_at());
        this.content = post.getContent();

        // Set the comment count and heart count
        //this.comment_count = commentRepository.getAllCommentByPostId(post.getId()).size();
        //this.heart_count = heartRepository.getAllHeartByPostId(post.getId()).size();
        this.comment_count=post.getComment_count();
        this.heart_count=post.getHeart_count();
    }

    // Get a snippet of the content with a maximum number of characters
//    private String getSnippet(String content, int maxChars) {
//        if (content.length() <= maxChars) {
//            return content;
//        } else {
//            return content.substring(0, maxChars);
//        }
//    }

    // Format the timestamp to a readable date and time string
    private String formatTimestamp(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }

    // Convert a list of Post entities to a list of PostListResDto
//    public static List<PostListResDto> convertToDtoList(List<Post> posts, CommentRepository commentRepository, HeartRepository heartRepository) {
//        return posts.stream()
//                .map(post -> {
//                    try {
//                        return new PostListResDto(post, commentRepository, heartRepository);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .collect(Collectors.toList());
//    }
}
