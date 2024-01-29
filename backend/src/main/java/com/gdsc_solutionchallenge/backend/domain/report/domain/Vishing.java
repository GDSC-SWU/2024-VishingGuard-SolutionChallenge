package com.gdsc_solutionchallenge.backend.domain.report.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Vishing {
    @DocumentId
    private String id;
    @NotNull
    private Long user_id;
    @NotNull
    private String script;
    @NotNull
    private String phone;
    @NotNull
    private String date;
    @NotNull
    private String time;
    @NotNull
    String keyword_comment;
}
