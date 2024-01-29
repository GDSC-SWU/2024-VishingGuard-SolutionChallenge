package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmishingKeyword {
    @DocumentId
    private String id;
    @NotNull
    private String smishing_keyword;

    @Builder
    public SmishingKeyword(String smishing_keyword){
        this.smishing_keyword=smishing_keyword;
    }
}