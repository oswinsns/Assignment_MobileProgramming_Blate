package com.example.aol_blate_mobprog;

import com.example.aol_blate_mobprog.models.Person;
import com.example.aol_blate_mobprog.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FirestoreManager {

    private static FirestoreManager instance;
    private final FirebaseFirestore db;
    private final CollectionReference usersRef;
    private final CollectionReference peopleRef;
    private FirebaseAuth auth;

    private FirestoreManager() {
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("user");
        peopleRef = db.collection("person");
        auth = FirebaseAuth.getInstance();
    }

    public static synchronized FirestoreManager getInstance() {
        if (instance == null) instance = new FirestoreManager();
        return instance;
    }

    public interface FirestoreCallback {
        void onSuccess(Object result);
        void onFailure(Exception e);
    }

    // --- USER FEATURES ---
    public void registerNewUser(User newUser, FirestoreCallback callback) {
        usersRef.document(String.valueOf(newUser.getId())).set(newUser)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Success"))
                .addOnFailureListener(callback::onFailure);
    }

    public void getAllCandidates(FirestoreCallback callback) {
        peopleRef.get().addOnSuccessListener(querySnapshot -> {
            List<Person> list = new ArrayList<>();
            for (QueryDocumentSnapshot doc : querySnapshot) list.add(doc.toObject(Person.class));
            callback.onSuccess(list);
        });
    }

    // 1. Get Current User
    public void getCurrentUser(FirestoreCallback callback) {
        // TESTING MODE: BYPASS AUTH CHECK
//        if (auth.getCurrentUser() == null) {
//            // FIX 1: Wrap the error message in a new Exception object
//            callback.onFailure(new Exception("No user logged in"));
//            return;
//        }
//        String userId = auth.getCurrentUser().getUid();
//
//        usersRef.document(userId).get().addOnSuccessListener(document -> {
//            if (document.exists()) {
//                callback.onSuccess(document.toObject(User.class));
//            } else {
//                // FIX 2: Wrap the error message in a new Exception object
//                callback.onFailure(new Exception("User data not found in database"));
//            }
//        }).addOnFailureListener(e -> {
//            // FIX 3: Pass the Exception 'e' directly (do not use .getMessage())
//            callback.onFailure(e);
//        });

        // USE HARDCODED ID
        String userId = "test_user_01";

        usersRef.document(userId).get().addOnSuccessListener(document -> {
            if (document.exists()) {
                callback.onSuccess(document.toObject(User.class));
            } else {
                callback.onFailure(new Exception("Test user not found! Did you create 'test_user_01' in Firestore?"));
            }
        }).addOnFailureListener(callback::onFailure);
    }

    // 2. Save Swipe Action
    public void saveSwipeAction(String targetUserId, boolean isLike) {
//        if (auth.getCurrentUser() == null) return; // Safety check
//
//        // THIS GETS THE REAL ID
//        String userId = auth.getCurrentUser().getUid();
//        String fieldName = isLike ? "accepted" : "rejected";
//
//        usersRef.document(userId).update(fieldName, FieldValue.arrayUnion(targetUserId));

        // HARDCODE THE TEST USER ID HERE TOO
        String userId = "test_user_01";

        String fieldName = isLike ? "accepted" : "rejected";

        // Debug Log to confirm it fires
        android.util.Log.d("DATING_APP", "Saving " + fieldName + " for " + targetUserId + " to user " + userId);

        usersRef.document(userId).update(fieldName, FieldValue.arrayUnion(targetUserId))
                .addOnSuccessListener(aVoid -> android.util.Log.d("DATING_APP", "Swipe Saved Successfully!"))
                .addOnFailureListener(e -> android.util.Log.e("DATING_APP", "Swipe Failed: " + e.getMessage()));
    }
}