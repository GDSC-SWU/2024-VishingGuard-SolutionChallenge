package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.gdsc_solutionchallenge.backend.domain.result.domain.MessageRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.MessageRequestDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class MessageService {
    public final MessageRepository messageRepository;
    public static final String COLLECTION_NAME = "Message";
    public List<Message> getMessage() throws Exception{
        List<Message> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Message.class));
        }
        return list;
    }

    public ResponseEntity<Object> isPhishingMessage(MessageRequestDto messageRequestDto){
        try{
            List<Message> messageList=messageRepository.getAllMessages();

            for (Message message: messageList){
                if (message != null && removeSpacesAndLowercase(messageRequestDto.getMessage())
                        .contains(removeSpacesAndLowercase(message.getMessageKeyword())))
                {
                    return ResponseEntity.ok(true);
                }

            }
            return ResponseEntity.ok(false);
        }catch (Exception e){
            // 예외 발생 시, 클라이언트에게 400 Bad Request 와 함께 예외 메시지를 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private String removeSpacesAndLowercase(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
