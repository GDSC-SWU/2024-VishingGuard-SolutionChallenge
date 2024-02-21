package com.gdsc_solutionchallenge.backend.domain.mypage.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateReqDto {
    private String username;
    private String password;
}
