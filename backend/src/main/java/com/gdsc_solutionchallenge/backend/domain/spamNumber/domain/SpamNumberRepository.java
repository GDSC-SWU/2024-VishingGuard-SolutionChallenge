package com.gdsc_solutionchallenge.backend.domain.spamNumber.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class SpamNumberRepository {
    private final Firestore firestore;

    public SpamNumberRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public SpamNumber saveNumber(SpamNumber spamNumber) throws Exception {
        CollectionReference spamNumbers = firestore.collection("spam_number");
        ApiFuture<DocumentReference> apiFuture = spamNumbers.add(spamNumber);
        DocumentReference documentReference = apiFuture.get();
        spamNumber.setId(documentReference.getId());
        return spamNumber;
    }

    public SpamNumber findById(String id) throws Exception{
        CollectionReference spamNumbers = firestore.collection("spam_number");
        DocumentReference documentReference = spamNumbers.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(SpamNumber.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<SpamNumber> getAllNumbers() throws Exception{
        CollectionReference spamNumbers = firestore.collection("spam_number");
        ApiFuture<QuerySnapshot> querySnapshot = spamNumbers.get();

        List<SpamNumber> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(SpamNumber.class));
        }

        return result;
    }
}
