package com.gdsc_solutionchallenge.backend.domain.board.comment.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.CommentRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // Save a new comment and return the result as CommentResDto
    public CommentResDto saveComment(Long userId, String postId, CommentReqDto commentReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        Comment comment = Comment.builder()
                .user(user)
                .post_id(post.getId())
                .user_id(user.getId())
                .content(commentReqDto.getContent())
                .build();

        commentRepository.save(comment);
        //post.setComment_count(post.getComment_count()+1);
        postRepository.upCommentCount(postId);

        return new CommentResDto(comment, post);
    }

    // Update an existing comment and return the result as CommentResDto
    public CommentResDto updateComment(Long userId, String postId, String commentId, CommentUpdateReqDto commentUpdateReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Comment not found");
        }

        comment.update(commentUpdateReqDto.getContent());
        commentRepository.update(comment);

        return new CommentResDto(comment, post);
    }

    // Get all comments associated with a specific post and return them as a list of CommentResDto
    public List<CommentResDto> getAllComments(Long userId, String postId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        List<Comment> comments = commentRepository.getAllCommentByPostId(postId);

        List<CommentResDto> commentResDtos = comments.stream()
                .map(comment -> new CommentResDto(comment, post))
                .collect(Collectors.toList());

        return commentResDtos;
    }

    // Delete a comment and return its ID
    public String deleteComment(Long userId, String postId, String commentId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Comment not found");
        }

        if (!comment.getPost_id().equals(postId)){
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Resource not found");
        } else if (!comment.getUser_id().equals(userId)){
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "No permission to delete");
        }

        //post.setComment_count(post.getComment_count()-1);
        postRepository.downCommentCount(postId);
        return commentRepository.delete(commentId);
    }
}
