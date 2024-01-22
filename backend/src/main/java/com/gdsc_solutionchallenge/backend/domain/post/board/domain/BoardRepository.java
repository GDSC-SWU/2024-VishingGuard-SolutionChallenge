package com.gdsc_solutionchallenge.backend.domain.post.board.domain;

import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrl;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class BoardRepository {
    private final Firestore firestore;

    public BoardRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public Board saveBoard(Board board) throws Exception {
        CollectionReference boards = firestore.collection("board");
        ApiFuture<DocumentReference> apiFuture = boards.add(board);
        DocumentReference documentReference = apiFuture.get();
        board.setId(documentReference.getId());
        return board;
    }

    public PhishingUrl findById(String id) throws Exception{
        CollectionReference phishingUrls = firestore.collection("board");
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



    public List<PhishingUrl> getAllBoards() throws Exception{
        CollectionReference phishingUrls = firestore.collection("board");
        ApiFuture<QuerySnapshot> querySnapshot = phishingUrls.get();

        List<PhishingUrl> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(PhishingUrl.class));
        }

        return result;
    }
}
