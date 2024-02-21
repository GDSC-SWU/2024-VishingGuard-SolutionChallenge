package com.gdsc_solutionchallenge.backend.domain.board.post.domain;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Firestore firestore;

    public PostRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Save a post to the Firestore collection
    public Post save(Post post) throws Exception {
        CollectionReference posts = firestore.collection("post");
        ApiFuture<DocumentReference> apiFuture = posts.add(post);
        DocumentReference documentReference = apiFuture.get();
        post.setId(documentReference.getId());
        return post;
    }

    // Delete a post from the Firestore collection based on its ID
    public String delete(String id) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = posts.document(id);
        documentReference.delete();
        return id;
    }

    // Update a post in the Firestore collection
    public Post update(Post post) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = firestore.collection("post").document(post.getId());

        // Create a map with data to be updated
        Map<String, Object> updates = new HashMap<>();
        updates.put("title", post.getTitle());
        updates.put("content", post.getContent());
        updates.put("updated_at", Timestamp.now());

        // Apply the updates to the document
        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // Wait for the result

        return post;
    }

    // Find a post in the Firestore collection based on its ID
    public Post findById(String id) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = posts.document(id); // Reference to the document with a specific ID
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Post.class);
        } else {
            return null;
        }
    }

    // Get a list of all posts ordered by creation time (descending)
    public List<Post> getAllPost() throws Exception {
        CollectionReference posts = firestore.collection("post");
        ApiFuture<QuerySnapshot> querySnapshot = posts.orderBy("created_at", Query.Direction.DESCENDING).get();

        List<Post> result = new ArrayList<>();
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            result.add(document.toObject(Post.class));
        }

        return result;
    }

    // Get a list of all post titles ordered by creation time (descending)
    public List<String> getAllTitles() throws Exception {
        CollectionReference posts = firestore.collection("post");
        ApiFuture<QuerySnapshot> querySnapshot = posts.orderBy("created_at", Query.Direction.DESCENDING).get();

        List<String> titles = querySnapshot.get().getDocuments()
                .stream()
                .map(document -> document.getString("title"))
                .filter(title -> title != null)
                .collect(Collectors.toList());

        return titles;
    }

    // Increment comment_count by 1
    public void upCommentCount(String postId) throws Exception {
        updateCommentCount(postId, 1);
    }

    // Decrement comment_count by 1
    public void downCommentCount(String postId) throws Exception {
        updateCommentCount(postId, -1);
    }

    private void updateCommentCount(String postId, int delta) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = firestore.collection("post").document(postId);

        // Atomically increment or decrement comment_count by delta
        Map<String, Object> updates = new HashMap<>();
        updates.put("comment_count", FieldValue.increment(delta));

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // Wait for the result
    }

    // Increment heart_count by 1
    public void upHeartCount(String postId) throws Exception {
        updateHeartCount(postId, 1);
    }

    // Decrement heart_count by 1
    public void downHeartCount(String postId) throws Exception {
        updateHeartCount(postId, -1);
    }

    private void updateHeartCount(String postId, int delta) throws Exception {
        CollectionReference posts = firestore.collection("post");
        DocumentReference documentReference = firestore.collection("post").document(postId);

        // Atomically increment or decrement comment_count by delta
        Map<String, Object> updates = new HashMap<>();
        updates.put("heart_count", FieldValue.increment(delta));

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.update(updates);
        writeResultApiFuture.get();  // Wait for the result
    }
}
