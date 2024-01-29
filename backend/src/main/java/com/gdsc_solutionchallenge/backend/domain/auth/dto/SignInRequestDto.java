package com.gdsc_solutionchallenge.backend.domain.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignInRequestDto {
    // 로그인
    private String email;
    private String password;
}