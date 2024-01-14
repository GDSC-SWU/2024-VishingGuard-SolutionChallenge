package com.gdsc_solutionchallenge.backend.domain.result.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class PhishingUrlRepository {
    private final Firestore firestore;

    public PhishingUrlRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public PhishingUrl saveURL(PhishingUrl phishingUrl) throws Exception {
        CollectionReference phishingUrls = firestore.collection("phishing_url");
        ApiFuture<DocumentReference> apiFuture = phishingUrls.add(phishingUrl);
        DocumentReference documentReference = apiFuture.get();
        phishingUrl.setId(documentReference.getId());
        return phishingUrl;
    }

    public PhishingUrl findById(String id) throws Exception{
        CollectionReference phishingUrls = firestore.collection("phishing_url");
        DocumentReference documentReference = phishingUrls.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(PhishingUrl.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<PhishingUrl> getAllSmishings() throws Exception{
        CollectionReference phishingUrls = firestore.collection("phishing_url");
        ApiFuture<QuerySnapshot> querySnapshot = phishingUrls.get();

        List<PhishingUrl> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(PhishingUrl.class));
        }

        return result;
    }

}
