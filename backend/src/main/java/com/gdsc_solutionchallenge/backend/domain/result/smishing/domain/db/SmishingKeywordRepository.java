package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain.db;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SmishingKeywordRepository {
    private final Firestore firestore;

    public SmishingKeywordRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    // Message Keyword 저장하는 메서드
    public SmishingKeyword saveMessage(SmishingKeyword smishingKeyword) throws Exception {
        CollectionReference smishings = firestore.collection("smishing_keyword");
        ApiFuture<DocumentReference> apiFuture = smishings.add(smishingKeyword);
        DocumentReference documentReference = apiFuture.get();
        smishingKeyword.setId(documentReference.getId());
        return smishingKeyword;
    }

    public SmishingKeyword findById(String id) throws Exception{
        CollectionReference smishings = firestore.collection("smishing_keyword");
        DocumentReference documentReference = smishings.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(SmishingKeyword.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<SmishingKeyword> getAllSmishingKeyword() throws Exception{
        CollectionReference smishings = firestore.collection("smishing_keyword");
        ApiFuture<QuerySnapshot> querySnapshot = smishings.get();

        List<SmishingKeyword> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(SmishingKeyword.class));
        }

        return result;
    }




}
