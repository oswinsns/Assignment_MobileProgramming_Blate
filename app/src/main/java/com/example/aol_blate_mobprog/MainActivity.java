package com.example.aol_blate_mobprog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText confirmPassword;   // NEW
    CheckBox chkTerms;

    Button loginButton;
    ImageView btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // View references
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextNumberPassword2);
        confirmPassword = findViewById(R.id.editTextConfirmPassword); // NEW
        chkTerms = findViewById(R.id.checkBox);

        TextView tv = findViewById(R.id.tvlinktoPage);

        // Clickable link
        String text = "Already Have an Account? Login";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLUE);
            }
        };

        ss.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);

        loginButton = findViewById(R.id.button2);

        loginButton.setOnClickListener(v -> {

            Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
            String confirmPasswordInput = confirmPassword.getText().toString().trim();
            boolean isTermsChecked = chkTerms.isChecked();

            boolean isValid = true;

            // EMAIL CHECK
            if (!emailInput.contains("@gmail.com")) {
                email.setError("Email must contain @gmail.com");
                email.startAnimation(shake);
                email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                isValid = false;
            } else {
                email.getBackground().clearColorFilter();
            }

            // PASSWORD LENGTH CHECK
            if (passwordInput.length() < 8) {
                password.setError("Password must be at least 8 characters");
                password.startAnimation(shake);
                password.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                isValid = false;
            } else {
                password.getBackground().clearColorFilter();
            }

            // CONFIRM PASSWORD MATCH CHECK
            if (!passwordInput.equals(confirmPasswordInput)) {
                confirmPassword.setError("Passwords do not match");
                confirmPassword.startAnimation(shake);
                confirmPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                isValid = false;
            } else {
                confirmPassword.getBackground().clearColorFilter();
            }

            // TERMS CHECK
            if (!isTermsChecked) {
                chkTerms.startAnimation(shake);
                Toast.makeText(MainActivity.this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (!isValid) return;

            // SUCCESS → MOVE TO NEXT ACTIVITY
            Intent intent = new Intent(MainActivity.this, AddProfileDetailActivity.class);
            intent.putExtra("email", emailInput);
            startActivity(intent);

            Toast.makeText(MainActivity.this, "🎉 Registration Successful!", Toast.LENGTH_LONG).show();
        });

        // Help popup
        showHelpDialog();
    }

    private void showHelpDialog() {
        btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_help);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
            TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
            Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

            tvTitle.setText("Registration Info");
            tvMessage.setText("Create your new account here. Please use a valid email address and create a strong password (at least 8 characters).");

            btnClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });
    }
}
