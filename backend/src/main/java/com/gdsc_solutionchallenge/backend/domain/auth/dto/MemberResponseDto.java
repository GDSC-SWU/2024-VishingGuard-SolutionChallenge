package com.gdsc_solutionchallenge.backend.domain.auth;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String username;
    private String email;
    private String profileImg;

    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .profileImg(member.getProfileImg()).build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .email(email)
                .profileImg(profileImg).build();
    }
}
