package com.gdsc_solutionchallenge.backend.domain.board.post.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.CommentRepository;
import com.gdsc_solutionchallenge.backend.domain.board.comment.dto.CommentResDto;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostListResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostReqDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    // Save a new post
    public PostResDto savePost(Long userId, PostReqDto postReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        //Post post = postReqDto.toEntity(user);
        Post post = Post.builder()
                .user(user)
                .user_id(userId)
                .title(postReqDto.getTitle())
                .content(postReqDto.getContent())
                .comment_count(0)
                .heart_count(0)
                .build();
        postRepository.save(post);
        //int comment_count = commentRepository.getAllCommentByPostId(post.getId()).size();
        //int heart_count = heartRepository.getAllHeartByPostId(post.getId()).size();
        return new PostResDto(post, true/*, comment_count, heart_count*/);
    }

    // Update an existing post
    public PostResDto updatePost(Long userId, String postId, PostUpdateReqDto postUpdateReqDto) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        if (!user.getId().equals(post.getUser_id())) {
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "No permission to modify");
        }

        post.update(postUpdateReqDto.getTitle(), postUpdateReqDto.getContent());
        postRepository.update(post);
        //int comment_count = commentRepository.getAllCommentByPostId(post.getId()).size();
        //int heart_count = heartRepository.getAllHeartByPostId(post.getId()).size();
        return new PostResDto(post, true/*, comment_count, heart_count*/);
    }

    // Find a post by its ID
    public PostResDto findPostById(Long userId, String postId) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }
        //int comment_count = commentRepository.getAllCommentByPostId(post.getId()).size();
        //int heart_count = heartRepository.getAllHeartByPostId(post.getId()).size();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        if (!user.getId().equals(post.getUser_id())) {
            return new PostResDto(post, false/*, comment_count, heart_count*/);
        }

        return new PostResDto(post, true/*, comment_count, heart_count*/);
    }

    // Get a list of all posts
    public List<PostListResDto> getAllPosts() throws Exception {
        List<Post> posts = postRepository.getAllPost();

        List<PostListResDto> postListResDtos = posts.stream()
                .map(post -> {
                    try {
                        return new PostListResDto(post);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return postListResDtos;
        //return PostListResDto.convertToDtoList(posts, commentRepository, heartRepository);
    }

    // Delete a post
    public String deletePost(Long userId, String postId) throws Exception {
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        if (!user.getId().equals(post.getUser_id())) {
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "No permission to delete");
        }

        return postRepository.delete(postId);
    }


}
