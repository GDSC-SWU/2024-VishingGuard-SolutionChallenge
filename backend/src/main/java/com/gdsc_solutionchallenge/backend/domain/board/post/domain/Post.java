package com.gdsc_solutionchallenge.backend.domain.board.post.domain;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    @DocumentId
    private String id;

    @NotNull
    private User user;

    @NotNull
    private Long user_id;

    @Size(max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    @ServerTimestamp
    private Timestamp created_at;

    @ServerTimestamp
    private Timestamp updated_at;
    @NotNull
    private int comment_count;
    @NotNull
    private int heart_count;

    @Builder
    public Post(User user, Long user_id, String title, String content, int comment_count, int heart_count) {
        this.user = user;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.comment_count=comment_count;
        this.heart_count=heart_count;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
        this.updated_at = Timestamp.now();
    }

    public Date getUpdated_at() {
        return updated_at != null ? updated_at.toDate() : Timestamp.now().toDate();
    }

    public Date getCreated_at() {
       return created_at != null ? created_at.toDate() : Timestamp.now().toDate();
    }
}
