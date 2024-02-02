package com.gdsc_solutionchallenge.backend.domain.report.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VishingKeyword {
    @DocumentId
    private String id;
    @NotNull
    private String vishing_keyword;

    @Builder
    public VishingKeyword(String vishing_keyword){
        this.vishing_keyword=vishing_keyword;
    }
}
