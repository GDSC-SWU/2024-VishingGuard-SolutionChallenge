package com.gdsc_solutionchallenge.backend.domain.board.comment.domain;


import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @DocumentId
    private String id;

    @NotNull
    private User user;

    @NotNull
    private String post_id;

    @NotNull
    private Long user_id;

    @NotNull
    private String content;

    @ServerTimestamp
    private Timestamp created_at;

    @ServerTimestamp
    private Timestamp updated_at;

    @Builder
    public Comment(User user, String post_id, Long user_id, String content) {
        this.user = user;
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
        this.updated_at = Timestamp.now();
    }

    public Date getUpdated_at() {
        return updated_at != null ? updated_at.toDate() : Timestamp.now().toDate();
    }

    public Date getCreated_at() {
        return created_at != null ? created_at.toDate() : Timestamp.now().toDate();
    }
}
