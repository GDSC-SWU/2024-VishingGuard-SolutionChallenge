package com.gdsc_solutionchallenge.backend.domain.board.heart.service;

import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.Heart;
import com.gdsc_solutionchallenge.backend.domain.board.heart.domain.HeartRepository;
import com.gdsc_solutionchallenge.backend.domain.board.heart.dto.HeartReqDto;
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

    public Boolean isHeart(HeartReqDto heartReqDto) throws Exception {
        User user = userRepository.findById(heartReqDto.getUser_Id());
        if (user == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        Post post = postRepository.findById(heartReqDto.getPost_Id());
        if (post == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "post not found");
        }

        boolean isHeart = heartRepository.findByUserIdAndPostId(user.getId(), post.getId());
        System.out.println(isHeart);

        // 하트가 설정 되어있다면 삭제하는 로직 구현
        if (isHeart) {
            heartRepository.delete(user.getId(), post.getId());
        } else {
            Heart heart = heartReqDto.toEntity(user.getId(), post.getId());
            heartRepository.save(heart);
        }

        return heartRepository.findByUserIdAndPostId(user.getId(), post.getId());

    }
}
