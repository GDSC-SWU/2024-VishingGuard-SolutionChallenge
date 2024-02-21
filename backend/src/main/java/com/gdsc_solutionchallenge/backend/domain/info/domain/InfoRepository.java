package com.gdsc_solutionchallenge.backend.domain.info.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InfoRepository {
    private final Firestore firestore;

    public InfoRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<ReportProcedure> getAllReportProcedure() throws Exception{
        CollectionReference reportProcedures = firestore.collection("report_procedure");
        ApiFuture<QuerySnapshot> querySnapshot = reportProcedures.orderBy("order", Query.Direction.ASCENDING).get();

        List<ReportProcedure> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(ReportProcedure.class));
        }

        return result;
    }

    public List<Prevention> getAllPrevention() throws Exception{
        CollectionReference preventions = firestore.collection("prevention");
        ApiFuture<QuerySnapshot> querySnapshot = preventions.orderBy("order", Query.Direction.ASCENDING).get();

        List<Prevention> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Prevention.class));
        }

        return result;
    }

    public List<ReportPlace> getAllReportPlace() throws Exception{
        CollectionReference reportPlaces = firestore.collection("report_place");
        ApiFuture<QuerySnapshot> querySnapshot = reportPlaces.orderBy("order", Query.Direction.ASCENDING).get();

        List<ReportPlace> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(ReportPlace.class));
        }

        return result;
    }
}
