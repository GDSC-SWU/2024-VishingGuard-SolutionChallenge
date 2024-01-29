package com.gdsc_solutionchallenge.backend.domain.auth.dto;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.Member;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {

    private Long id;
    private String username;
    private String email;

    static public MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}
