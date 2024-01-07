package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpamNumber {
    @DocumentId
    private String id;
    @NotNull
    private String number;
    @Builder
    public SpamNumber(String number){
        this.number=number;
    }
}
