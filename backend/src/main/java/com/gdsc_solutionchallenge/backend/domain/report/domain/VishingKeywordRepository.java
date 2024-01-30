package com.gdsc_solutionchallenge.backend.domain.report.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VishingKeywordRepository {
    private final Firestore firestore;

    public VishingKeywordRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public List<VishingKeyword> getAllVishingKeyword() throws Exception{
        CollectionReference vishings = firestore.collection("vishing_keyword");
        ApiFuture<QuerySnapshot> querySnapshot = vishings.get();

        List<VishingKeyword> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(VishingKeyword.class));
        }

        return result;
    }
}
