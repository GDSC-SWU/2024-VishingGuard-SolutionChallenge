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

    public Smishing save(Smishing smishing) throws Exception {
        CollectionReference smishings = firestore.collection("smishing");
        ApiFuture<DocumentReference> apiFuture = smishings.add(smishing);
        DocumentReference documentReference = apiFuture.get();
        smishing.setId(documentReference.getId());
        return smishing;
    }

    public List<Smishing> getAllScriptByUserId(Long userId) throws Exception{
        CollectionReference smishings = firestore.collection("smishing");

        // whereEqualTo를 사용하여 쿼리 생성
        Query query = smishings.whereEqualTo("user_id", userId);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Smishing> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Smishing.class));
        }

        return result;
    }
}
