package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SmishingRepository {
    private final Firestore firestore;

    public SmishingRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    // Message Keyword 저장하는 메서드
    public Smishing saveMessage(Smishing smishing) throws Exception {
        CollectionReference smishings = firestore.collection("smishing");
        ApiFuture<DocumentReference> apiFuture = smishings.add(smishing);
        DocumentReference documentReference = apiFuture.get();
        smishing.setId(documentReference.getId());
        return smishing;
    }

    public Smishing findById(String id) throws Exception{
        CollectionReference smishings = firestore.collection("smishing");
        DocumentReference documentReference = smishings.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Smishing.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<Smishing> getAllSmishings() throws Exception{
        CollectionReference smishings = firestore.collection("smishing");
        ApiFuture<QuerySnapshot> querySnapshot = smishings.get();

        List<Smishing> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Smishing.class));
        }

        return result;
    }




}
