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
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Heart {
    @DocumentId
    private String id;

    @NotNull
    private String user_id;

    @NotNull
    private String post_id;

    @ServerTimestamp
    private Timestamp created_at;

    @Builder
    public Heart(String user_id, String post_id) {
        this.user_id = user_id;
        this.post_id = post_id;
    }
}

