package com.gdsc_solutionchallenge.backend.domain.post.domain;

import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class Board {
    @DocumentId
    private Long id;

    @NotNull
    private User user;

    @Size(max = 50)
    @NotNull
    private String title;

    @NotNull
    private String content;

    //@CreationTimestamp
    private LocalDateTime created_at;

    //@UpdateTimestamp
    private LocalDateTime updated_at;

    @Builder
    public Board(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
}
