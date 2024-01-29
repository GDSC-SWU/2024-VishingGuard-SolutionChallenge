package com.gdsc_solutionchallenge.backend.domain.result.smishing.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class SmishingScriptRepository {
    private final Firestore firestore;

    public SmishingScriptRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public SmishingScript save(SmishingScript smishingScript) throws Exception {
        CollectionReference smishings = firestore.collection("smishing_script");
        ApiFuture<DocumentReference> apiFuture = smishings.add(smishingScript);
        DocumentReference documentReference = apiFuture.get();
        smishingScript.setId(documentReference.getId());
        return smishingScript;
    }
}
