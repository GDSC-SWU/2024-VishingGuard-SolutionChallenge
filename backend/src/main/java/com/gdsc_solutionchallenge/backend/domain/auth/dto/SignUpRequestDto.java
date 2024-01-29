package com.gdsc_solutionchallenge.backend.domain.auth.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {
    // 회원가입
    private String username;
    private String password;
    private String email;
    //private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .roles(roles)
                .build();
    }
}
