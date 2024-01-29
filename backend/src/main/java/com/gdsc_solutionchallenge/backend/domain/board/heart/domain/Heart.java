package com.gdsc_solutionchallenge.backend.domain.board.heart.domain;


import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class Heart {
    @DocumentId
    private String id;

    @NotNull
    private Long user_id;

    @NotNull
    private String post_id;

    @ServerTimestamp
    private Timestamp created_at;

    @Builder
    public Heart(Long user_id, String post_id) {
        this.user_id = user_id;
        this.post_id = post_id;
    }
}

