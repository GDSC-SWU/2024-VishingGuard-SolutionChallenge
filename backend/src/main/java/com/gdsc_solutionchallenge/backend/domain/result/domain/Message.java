package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*@Entity
@Table(name = "Message")*/
@Getter
@NoArgsConstructor
public class Message {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private String id;
    @NotNull
    //@JoinColumn(name = "message_content")
    private String messageKeyword;

    @Builder
    public Message(String messageKeyword){
        this.messageKeyword=messageKeyword;
    }
}