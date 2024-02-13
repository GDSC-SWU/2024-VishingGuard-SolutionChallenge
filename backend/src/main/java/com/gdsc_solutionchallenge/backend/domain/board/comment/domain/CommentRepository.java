package com.gdsc_solutionchallenge.backend.domain.board.comment.domain;

import com.gdsc_solutionchallenge.backend.domain.board.post.domain.Post;
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

    // Save a new comment to the Firestore database
    public Comment save(Comment comment) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        ApiFuture<DocumentReference> apiFuture = comments.add(comment);
        DocumentReference documentReference = apiFuture.get();
        comment.setId(documentReference.getId());
        return comment;
    }

    // Delete a comment from the Firestore database by its ID
    public String delete(String id) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = comments.document(id);
        documentReference.delete();
        return id;
    }

    // Update a comment in the Firestore database
    public Comment update(Comment comment) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = firestore.collection("comment").document(comment.getId());

        // Create a Map with update data
        Map<String, Object> updates = new HashMap<>();
        updates.put("content", comment.getContent());
        updates.put("updated_at", Timestamp.now());

        // Apply the update to the specified document
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // Wait for the result

        return comment;
    }

    // Find a comment by its ID in the Firestore database
    public Comment findById(String commentId) throws Exception {
        CollectionReference comments = firestore.collection("comment");
        DocumentReference documentReference = comments.document(commentId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Comment.class);
        } else {
            // Handle the case where no document matches the specified ID
            return null;
        }
    }

    // Get all comments associated with a specific post ID from the Firestore database
    public List<Comment> getAllCommentByPostId(String postId) throws Exception {
        CollectionReference comments = firestore.collection("comment");

        // Create a query using whereEqualTo
        Query query = comments.whereEqualTo("post_id", postId).orderBy("created_at", Query.Direction.ASCENDING);

        // Execute the query and retrieve the results
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        List<Comment> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            result.add(document.toObject(Comment.class));
        }
        return result;
    }
}
