package com.gdsc_solutionchallenge.backend.domain.board.heart.service;

import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.Heart;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.dto.HeartResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
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

    public HeartResDto isHeart(String userId, String postId) throws Exception {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        Post post = postRepository.findById(postId);
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        boolean isHeart = heartRepository.findByUserIdAndPostId(user.getId(), post.getId());
        System.out.println(isHeart);

        // 하트가 설정 되어있다면 삭제하는 로직 구현
        if (isHeart) {
            heartRepository.delete(user.getId(), post.getId());
            return new HeartResDto(user,post,false);
        } else {
            Heart heart = Heart.builder()
                    .user_id(user.getId())
                    .post_id(post.getId())
                    .build();
            heartRepository.save(heart);
            return new HeartResDto(user,post,true);
        }



    }
}
