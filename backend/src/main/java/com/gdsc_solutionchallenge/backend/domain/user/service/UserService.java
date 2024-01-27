package com.gdsc_solutionchallenge.backend.domain.user.service;

import com.gdsc_solutionchallenge.backend.domain.user.domain.Role;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.gdsc_solutionchallenge.backend.domain.user.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.user.dto.UserSignUpDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(UserSignUpDto userSignUpDto) throws Exception {
        if(userRepository.findByEmail(userSignUpDto.getEmail())==null){
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
        if(userRepository.findByNickname(userSignUpDto.getNickname())==null){
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "User not found");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }
}
