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

    public Vishing save(Vishing vishing) throws Exception {
        CollectionReference vishings = firestore.collection("vishing");
        ApiFuture<DocumentReference> apiFuture = vishings.add(vishing);
        DocumentReference documentReference = apiFuture.get();
        vishing.setId(documentReference.getId());
        return vishing;
    }

    public List<Vishing> getAllScriptByUserId(Long userId) throws Exception {
        CollectionReference vishings = firestore.collection("vishing");

        Query query = vishings.whereEqualTo("user_id", userId).orderBy("created_at", Query.Direction.DESCENDING);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Vishing> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Vishing.class));
        }

        return result;
    }

}
