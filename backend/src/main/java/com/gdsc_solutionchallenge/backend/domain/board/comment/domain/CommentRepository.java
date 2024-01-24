package com.gdsc_solutionchallenge.backend.domain.board.comment.domain;

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
public class CommentRepository {
    private final Firestore firestore;

    public CommentRepository(Firestore firestore) {
        this.firestore = firestore;
    }
    public Comment save(Comment comment) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        ApiFuture<DocumentReference> apiFuture = comments.add(comment);
        DocumentReference documentReference = apiFuture.get();
        comment.setId(documentReference.getId());
        return comment;
    }

    public String delete(String id) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = comments.document(id);
        documentReference.delete();
        return id;
    }

    public Comment update(Comment comment) throws Exception{
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = firestore.collection("comment").document(comment.getId());

        // 업데이트할 데이터를 Map으로 생성
        Map<String, Object> updates = new HashMap<>();
        updates.put("content", comment.getContent());
        updates.put("updated_at", Timestamp.now());

        // 해당 문서에 업데이트 적용
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // 결과를 기다림

        return comment;
    }

    public Comment findById(String commentId) throws Exception{
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = comments.document(commentId); // 특정 ID에 해당하는 문서를 참조
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Comment.class);
        } else {
            // 해당 ID에 매칭되는 문서가 없을 경우에 대한 처리
            return null;
        }
    }

    public List<Comment> getAllCommentByPostId(String postid) throws Exception{
        CollectionReference comments = firestore.collection("comment");

        // whereEqualTo를 사용하여 쿼리 생성
        Query query = comments.whereEqualTo("post_id", postid);

        // 쿼리를 실행하여 결과 가져오기
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Comment> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Comment.class));
        }
        return result;
    }

    public List<Comment> getAll() throws Exception{
        CollectionReference comments = firestore.collection("comment");
        ApiFuture<QuerySnapshot> querySnapshot = comments.get();

        List<Comment> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Comment.class));
        }

        return result;
    }
}
