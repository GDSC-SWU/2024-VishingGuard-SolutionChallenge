package com.gdsc_solutionchallenge.backend.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignInRequestDto {
    private String email;
    private String password;
}