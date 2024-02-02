package com.gdsc_solutionchallenge.backend.domain.route.domain;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RouteRepository {
    private final Firestore firestore;

    public RouteRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Route> getAll() throws Exception{
        CollectionReference routes = firestore.collection("route");
        ApiFuture<QuerySnapshot> querySnapshot = routes.get();

        List<Route> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Route.class));
        }

        return result;
    }
}
