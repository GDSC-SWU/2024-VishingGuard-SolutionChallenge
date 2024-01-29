package com.gdsc_solutionchallenge.backend.domain.auth.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtToken;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignInResDto {
    private JwtToken jwtToken;
    private Long userId;
}
