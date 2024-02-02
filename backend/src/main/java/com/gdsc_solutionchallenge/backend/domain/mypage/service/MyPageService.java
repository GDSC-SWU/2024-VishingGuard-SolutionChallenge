package com.gdsc_solutionchallenge.backend.domain.mypage.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.PostRepository;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostResDto;
import com.gdsc_solutionchallenge.backend.domain.board.post.dto.PostUpdateReqDto;
import com.gdsc_solutionchallenge.backend.domain.mypage.dto.UserUpdateReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    //private final BCryptPasswordEncoder encoder;
    private final PasswordEncoder passwordEncoder;

    public String update(Long userId, UserUpdateReqDto userUpdateReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        // Password 암호화
        String encodedPassword = passwordEncoder.encode(userUpdateReqDto.getPassword());
        user.updatePassword(encodedPassword);
        System.out.println(userUpdateReqDto.getUsername());
        user.updateUsername(userUpdateReqDto.getUsername());

        userRepository.save(user);

        return user.getUsername();
    }
}
