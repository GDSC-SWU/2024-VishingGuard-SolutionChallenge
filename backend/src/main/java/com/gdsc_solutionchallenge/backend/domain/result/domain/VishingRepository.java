package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class VishingRepository {
    private final Firestore firestore;

    public VishingRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    // Message Keyword 저장하는 메서드
    public Vishing saveMessage(Vishing vishing) throws Exception {
        CollectionReference vishings = firestore.collection("Vishing");
        ApiFuture<DocumentReference> apiFuture = vishings.add(vishing);
        DocumentReference documentReference = apiFuture.get();
        vishing.setId(documentReference.getId());
        return vishing;
    }
    // Vishing id로 해당 문서 찾는 메서드
    public Vishing findById(String id) throws Exception{
        CollectionReference vishings = firestore.collection("Vishing");
        DocumentReference documentReference = vishings.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Vishing.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }
    // Vishing 의 모든 문서 가져오는 메서드
    public List<Vishing> getAllVishings() throws Exception{
        CollectionReference vishings = firestore.collection("Vishing");
        ApiFuture<QuerySnapshot> querySnapshot = vishings.get();

        List<Vishing> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Vishing.class));
        }

        return result;
    }
}
