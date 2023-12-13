package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutionException;

public class MessageRepository {
    private final Firestore firestore;

    public MessageRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Message Keyword 저장하는 메서드
    public String saveMessageKeyword(Message message) throws ExecutionException, InterruptedException {
        CollectionReference messages = firestore.collection("Message");
        DocumentReference documentReference = messages.add(message).get();
        return documentReference.getId();
    }

}
