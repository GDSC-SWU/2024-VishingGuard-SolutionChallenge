package com.gdsc_solutionchallenge.backend.domain.user.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Builder
    public User(String email, String password, String nickname, String phone) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
    }

}
