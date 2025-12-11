package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvName = findViewById(R.id.NameProfileTV);
        TextView tvGender = findViewById(R.id.GenderProfileTV);
        TextView tvAbout = findViewById(R.id.AboutProfileTV);
        TextView tvAge = findViewById(R.id.AgeProfileTV);

        tvName.setText("Ava");
        tvGender.setText("Gender : Female");
        tvAge.setText("Age : 24");
        tvAbout.setText("Hi! I love traveling and trying out new food spots.");

        setupNavbar();
        showHelpDialog();
    }

    private void setupNavbar(){
        ImageView navChat = findViewById(R.id.ChatNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);

        navChat.setOnClickListener(v-> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
            finishAffinity();
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

            tvTitle.setText("Your Profile");
            tvMessage.setText("This is how your profile card appears to others. Make sure your photo and bio represent you well!");

            btnClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });
    }

}