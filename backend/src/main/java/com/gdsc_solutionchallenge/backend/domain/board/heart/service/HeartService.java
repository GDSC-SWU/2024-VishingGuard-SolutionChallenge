package com.gdsc_solutionchallenge.backend.domain.board.heart.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.Heart;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.dto.HeartResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public HeartResDto isHeart(Long userId, String postId) throws Exception {
        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        // Fetch the post by ID
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Post not found");
        }

        // Check if the user has already liked the post
        boolean isHeart = heartRepository.findByUserIdAndPostId(user.getId(), post.getId());

        // If the user has liked the post, remove the like; otherwise, add the like
        if (isHeart) {
            heartRepository.delete(user.getId(), post.getId());
            return new HeartResDto(user, post, false); // Heart removed
        } else {
            Heart heart = Heart.builder()
                    .user_id(user.getId())
                    .post_id(post.getId())
                    .build();
            heartRepository.save(heart);
            return new HeartResDto(user, post, true); // Heart added
        }
    }
}
