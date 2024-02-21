package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.firebase.database.annotations.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Smishing {
    @DocumentId
    private String id;
    @NotNull
    private Long user_id;
    @NotNull
    private String script;
    @NotNull
    private String phone;
    @ServerTimestamp
    private Timestamp created_at;
    private String keyword_comment;

    public Date getCreated_at() {
        return created_at != null ? created_at.toDate() : Timestamp.now().toDate();
    }
}
