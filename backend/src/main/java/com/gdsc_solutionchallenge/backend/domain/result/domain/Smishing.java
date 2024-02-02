package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Smishing {
    @DocumentId
    String id;
    @NotNull
    Long user_id;
    @NotNull
    String script;
    @NotNull
    String phone;

    String keyword_comment;
//    @NotNull
//    String url_comment;
}
