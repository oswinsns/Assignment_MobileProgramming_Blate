package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aol_blate_mobprog.models.Chat;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 1. Setup Data Dummy (Sesuai Gambar)
        ArrayList<Chat> items = new ArrayList<>();
        items.add(new Chat("Sophia", "Hey! How's your day going?", R.drawable.ic_launcher_background));
        items.add(new Chat("Ethan", "I'm excited to meet you!", R.drawable.ic_launcher_background));
        items.add(new Chat("Olivia", "Just finished a great workout.", R.drawable.ic_launcher_background));
        items.add(new Chat("Noah", "What are your plans for the weekend?", R.drawable.ic_launcher_background));
        items.add(new Chat("Ava", "I love your profile!", R.drawable.ic_launcher_background));
        items.add(new Chat("Liam", "Let's grab coffee sometime.", R.drawable.ic_launcher_background));

        // 2. Setup RecyclerView
        recyclerView = findViewById(R.id.rvChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(items, this);
        recyclerView.setAdapter(adapter);

        // 3. Navbar Logic (Tombol Pindah Halaman)
        setupNavbar();

        //buat button help
        showHelpDialog();
    }

    private void setupNavbar() {
        ImageView navProfile = findViewById(R.id.ProfileNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);

        navProfile.setOnClickListener(v-> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finishAffinity(); // Agar kalau diback tidak numpuk
        });

        navHistory.setOnClickListener(v->{
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            finishAffinity();
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