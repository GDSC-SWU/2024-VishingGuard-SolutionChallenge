package com.gdsc_solutionchallenge.backend.domain.post.domain;

import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Heart {
    @DocumentId
    private Long id;

    @NotNull
    private User user;

    @NotNull
    private Board board;

    //@CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public Heart(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
