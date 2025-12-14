package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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

    // Master List (Gudang Data) & Display List (Yang Tampil di Layar)
    private ArrayList<History> masterList = new ArrayList<>();
    private ArrayList<History> displayList = new ArrayList<>();

    private TextView btnAll, btnLike, btnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 1. Setup Data Dummy (Manual disini, gak pakai Firebase)
        setupDummyData();

        // 2. Setup RecyclerView
        recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Adapter dihubungkan ke displayList agar isinya bisa berubah-ubah saat difilter
        adapter = new HistoryAdapter(displayList, this);
        recyclerView.setAdapter(adapter);

        // 3. Setup Tombol Filter
        btnAll = findViewById(R.id.btnFilterAll);
        btnLike = findViewById(R.id.btnFilterLike);
        btnDislike = findViewById(R.id.btnFilterDislike);

        // 4. Logic Klik Tombol
        btnAll.setOnClickListener(v -> filterList("ALL"));
        btnLike.setOnClickListener(v -> filterList("Like"));
        btnDislike.setOnClickListener(v -> filterList("Dislike"));

        // 5. Setup Navbar
        setupNavbar();

        // Default awal: Tampilkan Semua
        filterList("ALL");

        showHelpDialog();
    }

    private void setupDummyData() {
        // Masukkan data dummy manual di sini
        // Format: Nama, Status (Like/Dislike), Tanggal, NamaFileGambar
        masterList.add(new History("Martin Scorcesse", "Like", "14 Dec 2025", "user_martin"));
        masterList.add(new History("Sophia Monica", "Dislike", "13 Dec 2025", "user_sophia"));
        masterList.add(new History("Made Artha", "Like", "12 Dec 2025", "user_madeartha"));
        masterList.add(new History("Rina Wulandari", "Like", "10 Dec 2025", "user_rinawulandari"));
        masterList.add(new History("Joji Similikiti", "Dislike", "08 Dec 2025", "user_joji"));
    }

    private void filterList(String type) {
        displayList.clear(); // Bersihkan layar dulu

        if (type.equals("ALL")) {
            // Masukkan semua data dari gudang
            displayList.addAll(masterList);
            updateButtonColor(btnAll);
        } else {
            // Saring satu per satu
            for (History item : masterList) {
                if (item.getStatus().equalsIgnoreCase(type)) {
                    displayList.add(item);
                }
            }
            // Ubah warna tombol sesuai yang diklik
            if (type.equals("Like")) updateButtonColor(btnLike);
            else updateButtonColor(btnDislike);
        }

        // Kabari adapter kalau data berubah
        adapter.notifyDataSetChanged();
    }

    private void updateButtonColor(TextView activeButton) {
        // Warna
        int inactiveColor = Color.parseColor("#E0E0E0"); // Abu-abu
        int activeColor = Color.parseColor("#FDD835");   // Kuning
        int textActive = Color.BLACK;
        int textInactive = Color.GRAY;

        // Reset tombol inactive
        setRoundedBackground(btnAll, inactiveColor);
        btnAll.setTextColor(textInactive);

        setRoundedBackground(btnLike, inactiveColor);
        btnLike.setTextColor(textInactive);

        setRoundedBackground(btnDislike, inactiveColor);
        btnDislike.setTextColor(textInactive);

        // Set tombol active
        setRoundedBackground(activeButton, activeColor);
        activeButton.setTextColor(textActive);
    }

    // --- FUNGSI RAHASIA BIAR TETAP ROUNDED ---
    private void setRoundedBackground(TextView view, int color) {
        android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
        shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        shape.setCornerRadius(50); // <-- Ini yang bikin rounded! (Ganti angka ini kalau kurang bulat)
        shape.setColor(color);     // <-- Ini yang ganti warna
        view.setBackground(shape); // Pasang ke tombol
    }

    private void setupNavbar() {
        ImageView navChat = findViewById(R.id.ChatNav); // Pastikan ID ini ada di XML
        ImageView navProfile = findViewById(R.id.ProfileNav);

        if (navChat != null) navChat.setOnClickListener(v -> {
            startActivity(new Intent(this, ChatActivity.class));
            finishAffinity();
        });

        if (navProfile != null) navProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
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
