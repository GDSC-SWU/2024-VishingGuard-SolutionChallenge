package com.gdsc_solutionchallenge.backend.domain.board.heart.domain;


import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class HeartRepository {
    private final Firestore firestore;

    public HeartRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public Heart save(Heart heart) throws Exception {
        CollectionReference hearts = firestore.collection("heart");
        ApiFuture<DocumentReference> apiFuture = hearts.add(heart);
        DocumentReference documentReference = apiFuture.get();
        heart.setId(documentReference.getId());
        return heart;
    }

    public void delete(Long userId, String postId) throws Exception {
        CollectionReference hearts = firestore.collection("heart");

        Query query = hearts.whereEqualTo("user_id", userId).whereEqualTo("post_id", postId);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
            documentSnapshot.getReference().delete();
            System.out.println("삭제");
        }

    }

    public Boolean findByUserIdAndPostId(Long userId, String postId) throws Exception{
        CollectionReference hearts = firestore.collection("heart");
        Query query = hearts.whereEqualTo("user_id", userId).whereEqualTo("post_id", postId);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        return !querySnapshot.isEmpty();
    }

    public Post findById(String id) throws Exception{
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = posts.document(id); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Post.class);
        } else {
            return null;
        }
    }

    public List<Heart> getAllHeartByPostId(String postId) throws Exception{
        CollectionReference hearts = firestore.collection("heart");

        Query query = hearts.whereEqualTo("post_id", postId);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Heart> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Heart.class));
        }
        return result;
    }

}
