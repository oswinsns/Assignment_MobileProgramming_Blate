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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aol_blate_mobprog.models.Chat;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private ArrayList<Chat> chatList;
    private FirebaseFirestore db;

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

        fetchDataFromFirebase();
        setupNavbar();
        showHelpDialog();
    }

    private void fetchDataFromFirebase() {
        // ambil dari collection namanya person
        db.collection("person")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        chatList.clear();

                        if (task.getResult().isEmpty()) {
                            Log.w("FIRESTORE", "Collection person kosong."); //kalo ga ada isinya
                        }

                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            // ambil data yang diperlukan
                            String name = doc.getString("name");
                            String about = doc.getString("about"); // pake aboutnya buat last message
                            String profile = doc.getString("profile"); // buat profilenya

                            // kalau null / ga ada isinya
                            if (name == null) name = "User Tak Dikenal";
                            if (about == null) about = "Halo! Mari ngobrol.";
                            if (profile == null) profile = "default_avatar";

                            // masukkan ke List
                            chatList.add(new Chat(name, about, profile));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("FIRESTORE", "Gagal ambil data: ", task.getException());
                    }
                });
    }

    private void setupNavbar() {
        ImageView navProfile = findViewById(R.id.ProfileNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);

        if(navProfile != null) navProfile.setOnClickListener(v-> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        if(navHistory != null) navHistory.setOnClickListener(v->{
            startActivity(new Intent(this, HistoryActivity.class));
        });
    }
    private void showHelpDialog(){
        ImageView btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v->{
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_help);
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