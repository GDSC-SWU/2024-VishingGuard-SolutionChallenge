package com.gdsc_solutionchallenge.backend.domain.board.post.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {
    private final Firestore firestore;

    public PostRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public Post save(Post post) throws Exception {
        CollectionReference posts = firestore.collection("post");
        ApiFuture<DocumentReference> apiFuture = posts.add(post);
        DocumentReference documentReference = apiFuture.get();
        post.setId(documentReference.getId());
        return post;
    }

    public String delete(String id) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = posts.document(id);
        documentReference.delete();
        return id;
    }

    public Post update(Post post) throws Exception{
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = firestore.collection("post").document(post.getId());

        // 업데이트할 데이터를 Map으로 생성
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", post.getTitle());
        updates.put("content", post.getContent());
        updates.put("updated_at", Timestamp.now());

        // 해당 문서에 업데이트 적용
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // 결과를 기다림

        return post;
    }

    public Post findById(String id) throws Exception{
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = posts.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Post.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<Post> getAll() throws Exception{
        CollectionReference posts = firestore.collection("post");
        ApiFuture<QuerySnapshot> querySnapshot = posts.get();

        List<Post> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Post.class));
        }

        return result;
    }
}
