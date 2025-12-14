package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvName = findViewById(R.id.NameProfileTV);
        TextView tvGender = findViewById(R.id.GenderProfileTV);
        TextView tvAbout = findViewById(R.id.AboutProfileTV);
        TextView tvHobby1 = findViewById(R.id.Hobby1ProfileTV);
        TextView tvAge = findViewById(R.id.AgeProfileTV);
        Button btnEdit = findViewById(R.id.EditProfileBtn);

        // retrieve Data
        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);

        String savedName = prefs.getString("saved_name", "No Name");
        String savedGender = prefs.getString("saved_gender", "-");
        String savedDob = prefs.getString("saved_dob", "");
        String savedHobby = prefs.getString("saved_hobby", "");
        String savedAddress = prefs.getString("saved_address", "-");
        String savedReligion = prefs.getString("saved_religion", "-");

        // Set Nama & Gender
        tvName.setText(savedName);
        tvGender.setText("Gender : " + savedGender);

        // Set Hobi (Logic: Hanya tampilkan jika tidak kosong)
        if (!savedHobby.isEmpty() && !savedHobby.equals("-")) {
            tvHobby1.setText(savedHobby);
            tvHobby1.setVisibility(View.VISIBLE);
        } else {
            tvHobby1.setVisibility(View.GONE);
        }

        // Set About (Tambahkan Agama di sini)
        String aboutText = "Hi! I am " + savedName + ".\n" +
                "Currently living in " + savedAddress + ".\n" +
                "Born on " + (savedDob.isEmpty() ? "-" : savedDob) + ".\n" +
                "Religion: " + savedReligion + "."; // <--- Tampilkan Agama
        tvAbout.setText(aboutText);

        // Hitung Umur
        if (!savedDob.isEmpty()) {
            String ageString = calculateAge(savedDob);
            tvAge.setText("Age : " + ageString);
            tvAge.setVisibility(View.VISIBLE);
        } else {
            tvAge.setVisibility(View.GONE);
        }

        // Tombol Edit (Kirim sinyal Edit Mode)
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddProfileDetailActivity.class);
            intent.putExtra("IS_EDIT_MODE", true);
            startActivity(intent);
        });

        setupNavbar();
        showHelpDialog();
    }

    private String calculateAge(String dobString) {
        try {
            String yearString = dobString.replaceAll("[^0-9]", "");
            if (yearString.length() >= 4) {
                String yearOnly = yearString.substring(yearString.length() - 4);
                int birthYear = Integer.parseInt(yearOnly);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int age = currentYear - birthYear;
                return String.valueOf(age);
            }
        } catch (Exception e) {
            return "-";
        }
        return "-";
    }

    private void setupNavbar(){
        ImageView navChat = findViewById(R.id.ChatNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);
        ImageView navDiscover = findViewById(R.id.DiscoverNav);

        if (navChat != null) {
            navChat.setOnClickListener(v-> {
                Intent intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
            });
        }
        if (navHistory != null) {
            navHistory.setOnClickListener(v->{
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
            });
        }
        if (navDiscover != null) {
            navDiscover.setOnClickListener(v -> {
                Intent intent = new Intent(this, DiscoverActivity.class);
                startActivity(intent);
            });
        }
    }

    private void showHelpDialog(){
        ImageView btnHelp = findViewById(R.id.btnHelp);
        if(btnHelp != null) {
            btnHelp.setOnClickListener(v -> {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_help);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
                TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
                Button btnClose = dialog.findViewById(R.id.btnCloseDialog);
                if (tvTitle != null) tvTitle.setText("Your Profile");
                if (tvMessage != null) tvMessage.setText("This is how your profile card appears to others.");
                if (btnClose != null) {
                    btnClose.setOnClickListener(view -> dialog.dismiss());
                }
                dialog.show();
            });
        }
    }
}