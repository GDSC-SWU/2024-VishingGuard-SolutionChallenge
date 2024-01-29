package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Smishing {
    @DocumentId
    private String id;
    @NotNull
    private String smishingKeyword;

    @Builder
    public Smishing(String smishingKeyword){
        this.smishingKeyword=smishingKeyword;
    }
}