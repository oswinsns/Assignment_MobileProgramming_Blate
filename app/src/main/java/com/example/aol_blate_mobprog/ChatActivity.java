package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aol_blate_mobprog.models.Chat;
import com.example.aol_blate_mobprog.models.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private ArrayList<Chat> chatList;
    private FirebaseFirestore db;
    private FirestoreManager firestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatList = new ArrayList<>();
        recyclerView = findViewById(R.id.rvChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatAdapter(chatList, this);
        recyclerView.setAdapter(adapter);

        // inisialisasi database firabasenya
        db = FirebaseFirestore.getInstance();
        firestoreManager = FirestoreManager.getInstance();

        fetchDataFromFirebase();
        setupNavbar();
        showHelpDialog();
    }

    private void fetchDataFromFirebase() {
        firestoreManager.getCurrentUser(new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) { // <--- CHANGE THIS FROM 'User user' TO 'Object result'

                // 1. Cast the Object to User
                User user = (User) result;

                // 2. Now you can use 'user' normally
                List<String> acceptedIds = user.getAccepted();

                if (acceptedIds == null || acceptedIds.isEmpty()) {
                    chatList.clear();
                    adapter.notifyDataSetChanged();
                    Log.d("CHAT", "User hasn't accepted anyone yet.");
                    return;
                }

                fetchMatches(acceptedIds);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ChatActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method
    private void fetchMatches(List<String> acceptedIds) {
        Log.d("CHAT_DEBUG", "I am looking for these IDs: " + acceptedIds.toString());

        db.collection("person")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        chatList.clear();
                        Log.d("CHAT_DEBUG", "I found " + task.getResult().size() + " people in the 'person' collection.");

                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String personId = doc.getId();
                            Log.d("CHAT_DEBUG", "Checking Person ID: '" + personId + "'");

                            // THE CRITICAL CHECK
                            if (acceptedIds.contains(personId)) {
                                Log.d("CHAT_DEBUG", "MATCH FOUND! Adding " + personId);

                                String name = doc.getString("name");
                                String about = doc.getString("about");
                                String profile = doc.getString("profile");

                                if (name == null) name = "Unknown User";
                                if (about == null) about = "Let's start chatting!";
                                if (profile == null) profile = "avatar_1";

                                chatList.add(new Chat(name, about, profile));
                            } else {
                                Log.d("CHAT_DEBUG", "No match for " + personId);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("CHAT_DEBUG", "Error fetching people: ", task.getException());
                    }
                });
    }

    private void setupNavbar() {
        ImageView navProfile = findViewById(R.id.ProfileNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);
        ImageView navDiscover = findViewById(R.id.DiscoverNav); // Ensure this ID exists in your XML

        if(navProfile != null) navProfile.setOnClickListener(v-> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        if(navHistory != null) navHistory.setOnClickListener(v->{
            startActivity(new Intent(this, HistoryActivity.class));
        });
        if(navDiscover != null) navDiscover.setOnClickListener(v->{
            startActivity(new Intent(this, DiscoverActivity.class));
        });
    }

    private void showHelpDialog(){
        ImageView btnHelp = findViewById(R.id.btnHelp);
        if (btnHelp != null) { // Added null check for safety
            btnHelp.setOnClickListener(v->{
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_help);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
                TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
                Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

                tvTitle.setText("Chat Safety");
                tvMessage.setText("Stay polite when chatting. Never share financial information or passwords with anyone.");

                btnClose.setOnClickListener(view -> {
                    dialog.dismiss();
                });
                dialog.show();
            });
        }
    }
}