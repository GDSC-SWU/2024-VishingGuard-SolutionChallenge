package com.gdsc_solutionchallenge.backend.domain.result.controller;

import com.gdsc_solutionchallenge.backend.domain.result.domain.Message;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageControllerTest {
    @Test
    public void saveMessageTest() throws Exception{
        Message message = Message.builder()
                .messageKeyword("Test content")
                .build();

        // Firestore 인스턴스 가져오기
        Firestore firestore = FirestoreClient.getFirestore();

        // Firestore에 데이터 추가
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("messageKeyword", message.getMessageKeyword());

        DocumentReference documentReference = firestore.collection("messages").add(messageData).get();
        String generatedId = documentReference.getId();
        System.out.println(generatedId);
    }


}