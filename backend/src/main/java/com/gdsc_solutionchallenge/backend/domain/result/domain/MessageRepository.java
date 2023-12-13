package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Repository
public class MessageRepository {
    private final Firestore firestore;

    public MessageRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    // Message Keyword 저장하는 메서드
    public Message saveMessage(Message message) throws Exception {
        CollectionReference messages = firestore.collection("Message");
        messages.add(message);
        return message;
    }

    public List<Message> getAllMessages() throws Exception{
        CollectionReference messages = firestore.collection("Message");
        ApiFuture<QuerySnapshot> querySnapshot = messages.get();

        List<Message> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Message.class));
        }

        return result;
    }


}
