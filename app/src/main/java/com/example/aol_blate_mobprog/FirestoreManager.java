package com.example.aol_blate_mobprog;

import com.example.aol_blate_mobprog.models.Person;
import com.example.aol_blate_mobprog.models.User;
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

    private FirestoreManager() {
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
        peopleRef = db.collection("people");
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
}