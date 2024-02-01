package com.gdsc_solutionchallenge.backend.domain.info.domain;

import com.gdsc_solutionchallenge.backend.domain.route.domain.Route;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PreventionRepository {
    private final Firestore firestore;

    public PreventionRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Prevention> getAll() throws Exception{
        CollectionReference preventions = firestore.collection("prevention");
        ApiFuture<QuerySnapshot> querySnapshot = preventions.get();

        List<Prevention> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Prevention.class));
        }

        return result;
    }
}
