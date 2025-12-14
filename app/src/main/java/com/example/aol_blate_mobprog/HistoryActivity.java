package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aol_blate_mobprog.models.History;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setup Data Dummy
        ArrayList<History> list = new ArrayList<>();
        list.add(new History("Maria", "Today", "Liked", R.drawable.ic_launcher_background));
        list.add(new History("John", "Today", "Passed", R.drawable.ic_launcher_background));
        list.add(new History("Robert", "Yesterday", "Liked", R.drawable.ic_launcher_background));
        list.add(new History("Alice", "2 Days Ago", "Liked", R.drawable.ic_launcher_background));
        list.add(new History("Mike", "Last Week", "Passed", R.drawable.ic_launcher_background));

        // Setup RecyclerView
        recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(list, this);
        recyclerView.setAdapter(adapter);

        setupNavbar();

        showHelpDialog();
    }

    private void setupNavbar() {
        ImageView navChat = findViewById(R.id.ChatNav);
        ImageView navProfile = findViewById(R.id.ProfileNav);
        ImageView navDiscover = findViewById(R.id.DiscoverNav);

        // ImageView navHome = findViewById(R.id.nav_home);

        navChat.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, ChatActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, ProfileActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        navDiscover.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, DiscoverActivity.class);
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

            tvTitle.setText("Activity Log");
            tvMessage.setText("This page tracks your activity history. Here you can see who you have 'Liked' or 'Passed' previously.");

            btnClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });

    }
}