package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // <-- OPEN layout named login.xml

        //Buat button help pojok kanan
        showHelpDialog();
    }

    private void showHelpDialog() {
        ImageView btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_help);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
            TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
            Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

            tvTitle.setText("Login Help");
            tvMessage.setText("Enter your registered email and password to log in. If you forgot your password, please contact admin support.");

            btnClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });

    }
}