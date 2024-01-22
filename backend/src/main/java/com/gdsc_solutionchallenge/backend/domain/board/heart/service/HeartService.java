package com.gdsc_solutionchallenge.backend.domain.board.heart.service;

import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.Heart;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Boolean isHeart(String userId, String postId) throws Exception {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        boolean isHeart = heartRepository.findByUserIdAndPostId(user.getId(), post.getId());

        // 하트가 설정 되어있다면 삭제하는 로직 구현
        if (isHeart) {
            // 하트 삭제
            heartRepository.delete(user.getId(), post.getId());
        } else {
            // 하트 저장
            heartRepository.save(new Heart(user, post));
        }

        return heartRepository.findByUserIdAndPostId(user.getId(), post.getId());

    }
}
