package com.gdsc_solutionchallenge.backend.domain.auth.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;

    static public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}
