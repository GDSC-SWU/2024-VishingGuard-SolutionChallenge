package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @DocumentId
    private String id;
    @NotNull

    private String messageKeyword;

    @Builder
    public Message(String messageKeyword){
        this.messageKeyword=messageKeyword;
    }
}