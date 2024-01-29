package com.gdsc_solutionchallenge.backend.domain.spamNumber.domain;

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

    private String name;
    @Builder
    public SpamNumber(String number,String name){
        this.number=number;
        this.name=name;
    }
}
