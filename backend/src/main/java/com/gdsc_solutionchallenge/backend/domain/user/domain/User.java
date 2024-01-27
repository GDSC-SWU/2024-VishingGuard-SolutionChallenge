package com.gdsc_solutionchallenge.backend.domain.user.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    @DocumentId
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Size(min = 2, max = 8)
    @NotNull
    private String nickname;

    @Size(max = 11)
    @NotNull
    private String phone;

    private String imageUrl;

    private Role role;

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰
    private SocialType socialType;

//    @Builder
//    public User(String email, String password, String nickname, String phone) {
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.phone = phone;
//    }

    // 유저 권한 설정 메소드


    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }
    public void updatePhone(String phone){
        this.phone = phone;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    //비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

}
