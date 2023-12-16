package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*@Entity
@Table(name = "Message")*/
@Getter
@Setter
@NoArgsConstructor
public class Message {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @DocumentId
    private String id;
    @NotNull
    //@JoinColumn(name = "message_content")
    private String messageKeyword;

    @Builder
    public Message(String messageKeyword){
        this.messageKeyword=messageKeyword;
    }
}