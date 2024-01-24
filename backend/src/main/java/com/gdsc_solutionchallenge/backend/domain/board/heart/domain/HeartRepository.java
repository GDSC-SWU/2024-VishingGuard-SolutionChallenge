package com.gdsc_solutionchallenge.backend.domain.board.heart.domain;

import com.gdsc_solutionchallenge.backend.domain.board.comment.domain.Comment;
import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
import com.gdsc_solutionchallenge.backend.domain.user.domain.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void delete(String userId, String postId) throws Exception {
        CollectionReference hearts = firestore.collection("heart");

        // userId와 postId에 해당하는 문서를 삭제하는 쿼리 생성
        Query query = hearts.whereEqualTo("user_id", userId).whereEqualTo("post_id", postId);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        // 결과에서 문서 가져오기
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
            documentSnapshot.getReference().delete();
            System.out.println("삭제");
        }

    }

    public Boolean findByUserIdAndPostId(String userId, String postId) throws Exception{
        CollectionReference hearts = firestore.collection("heart");
        Query query = hearts.whereEqualTo("user_id", userId).whereEqualTo("post_id", postId);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        // 결과에서 문서 가져오기
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
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<Heart> getAllHeartByPostId(String postid) throws Exception{
        CollectionReference hearts = firestore.collection("heart");

        // whereEqualTo를 사용하여 쿼리 생성
        Query query = hearts.whereEqualTo("post_id", postid);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Heart> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Heart.class));
        }
        return result;
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
