package com.gdsc_solutionchallenge.backend.domain.board.post.domain;

import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    @DocumentId
    private String id;

    @NotNull
    private User user;

    @Size(max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    @ServerTimestamp
    private Timestamp created_at;

    @ServerTimestamp
    private Timestamp updated_at;

    @Builder
    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
        this.updated_at = Timestamp.now();
    }
}
