package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aol_blate_mobprog.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextTextEmailAddress, editTextNumberPassword2;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 1. Initialize Views
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextNumberPassword2 = findViewById(R.id.editTextNumberPassword2);
        btnLogin = findViewById(R.id.button2); // The ID from your XML is "button2"

        // 2. Set Login Button Listener
        btnLogin.setOnClickListener(v -> handleLogin());

        // 3. Setup Help Dialog
        showHelpDialog();
    }

    private void handleLogin() {
        String email = editTextTextEmailAddress.getText().toString().trim();
        String password = editTextNumberPassword2.getText().toString().trim();

        // Basic Validation
        if (email.isEmpty()) {
            editTextTextEmailAddress.setError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            editTextNumberPassword2.setError("Password is required");
            return;
        }

        // Call FirestoreManager to check credentials
        FirestoreManager.getInstance().loginUser(email, password, new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                // Login Successful
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Navigate to DiscoveryActivity
                Intent intent = new Intent(LoginActivity.this, DiscoverActivity.class);
                startActivity(intent);
                finish(); // Prevents user from going back to login screen using 'Back' button
            }

            @Override
            public void onFailure(Exception e) {
                // Login Failed - User not in database or wrong password
                Toast.makeText(LoginActivity.this, "User is not registered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHelpDialog() {
        ImageView btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_help);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
            TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
            Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

            // Check if views exist to prevent crashes if dialog xml is missing items
            if(tvTitle != null) tvTitle.setText("Login Help");
            if(tvMessage != null) tvMessage.setText("Enter your registered email and password to log in. If you forgot your password, please contact admin support.");

            if(btnClose != null) btnClose.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        });
    }
}