package com.example.aol_blate_mobprog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    RadioGroup genderGroup;
    CheckBox chkTerms;

    Button loginButton;




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

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextNumberPassword2);
        genderGroup = findViewById(R.id.genderGroup);
        chkTerms = findViewById(R.id.checkBox);
        TextView tv = findViewById(R.id.tvlinktoPage);

        String text = "Already Have an Account? Login";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);   // underline like HTML <a>
                ds.setColor(Color.BLUE);     // link color
            }
        };

        ss.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.TRANSPARENT);



        loginButton = findViewById(R.id.button2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                int selectedGenderId = genderGroup.getCheckedRadioButtonId();
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

                // PASSWORD CHECK
                if (passwordInput.length() < 8) {
                    password.setError("Password must be at least 8 characters");
                    password.startAnimation(shake);
                    password.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    isValid = false;
                } else {
                    password.getBackground().clearColorFilter();
                }

                // GENDER CHECK
                if (selectedGenderId == -1) {
                    Toast.makeText(MainActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                // TERMS CHECK
                if (!isTermsChecked) {
                    chkTerms.startAnimation(shake);
                    Toast.makeText(MainActivity.this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if (!isValid) return;

                // SUCCESS → MOVE TO NEXT ACTIVITY
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("email", emailInput);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "🎉 Login Successful!", Toast.LENGTH_LONG).show();
            }
        });
    }
}