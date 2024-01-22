package com.gdsc_solutionchallenge.backend.domain.user.domain;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.result.domain.PhishingUrl;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final Firestore firestore;

    public UserRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public User save(User user) throws Exception {
        CollectionReference users = firestore.collection("user");
        ApiFuture<DocumentReference> apiFuture = users.add(user);
        DocumentReference documentReference = apiFuture.get();
        user.setId(documentReference.getId());
        return user;
    }

    public User findById(String id) throws Exception{
        CollectionReference users = firestore.collection("user");
        DocumentReference documentReference = users.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(User.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public User findByNickname(String nickname) throws Exception{
        CollectionReference users = firestore.collection("user");

        // whereEqualTo를 사용하여 쿼리 생성
        Query query = users.whereEqualTo("nickname", nickname);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        // 결과에서 문서 가져오기
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
            return documentSnapshot.toObject(User.class);
        } else {
            // 해당 조건에 맞는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<User> getAllUsers() throws Exception{
        CollectionReference users = firestore.collection("user");
        ApiFuture<QuerySnapshot> querySnapshot = users.get();

        List<User> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(User.class));
        }

        return result;
    }
}
