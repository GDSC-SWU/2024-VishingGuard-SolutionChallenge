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
    @NotNull
    private String name;
    private int count;
    @Builder
    public SpamNumber(String number,String name, int count){
        this.number=number;
        this.name=name;
        this.count=count;
    }
}
