package com.gdsc_solutionchallenge.backend.domain.board.heart.domain;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Heart {
    @DocumentId
    private String id;

    @NotNull
    private User user;

    @NotNull
    private Post post;

    @ServerTimestamp
    private Timestamp created_at;

    @Builder
    public Heart(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
