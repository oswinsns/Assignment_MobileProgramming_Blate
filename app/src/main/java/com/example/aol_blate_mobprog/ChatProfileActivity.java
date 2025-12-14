package com.example.aol_blate_mobprog;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aol_blate_mobprog.models.Person;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatProfileActivity extends AppCompatActivity {

    private ImageView ivProfile, btnBack;
    private TextView tvName, tvGender, tvAbout, tvHobbies, tvAge, tvReligion, tvDomicile, tvJob;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_profile);

        // 1. Initialize Views
        btnBack = findViewById(R.id.btnBackFromProfile);
        ivProfile = findViewById(R.id.ivProfilePic);
        tvName = findViewById(R.id.tvProfileName);
        tvGender = findViewById(R.id.tvProfileGender);
        tvAbout = findViewById(R.id.tvProfileAbout);
        tvHobbies = findViewById(R.id.tvProfileHobbies);
        tvAge = findViewById(R.id.tvProfileAge);
        tvReligion = findViewById(R.id.tvProfileReligion);
        tvDomicile = findViewById(R.id.tvProfileDomicile);
        tvJob = findViewById(R.id.tvProfileJob);

        db = FirebaseFirestore.getInstance();

        // 2. Get Data passed from ChatDetailActivity
        // We expect "userName" to be passed in the Intent
        String nameToFind = getIntent().getStringExtra("userName");

        if (nameToFind != null) {
            tvName.setText(nameToFind);
            loadPersonData(nameToFind);
        } else {
            Toast.makeText(this, "No user data received", Toast.LENGTH_SHORT).show();
        }

        // 3. Back Button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadPersonData(String name) {
        // Find the person in the "person" collection by Name
        db.collection("person")
                .whereEqualTo("name", name)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        // Convert the document to a Person object
                        Person p = querySnapshot.getDocuments().get(0).toObject(Person.class);
                        if (p != null) {
                            updateUI(p);
                        }
                    } else {
                        Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Log.e("CHAT_PROFILE", "Error loading profile: " + e.getMessage()));
    }

    private void updateUI(Person p) {
        // Gender Logic
        String genderStr = p.isGender() ? "Male" : "Female";
        tvGender.setText("Gender: " + genderStr);

        // Basic Info
        tvAbout.setText(p.getAbout());
        tvReligion.setText("Religion: " + p.getReligion());
        tvDomicile.setText("Domicile: " + p.getDomicile());
        tvJob.setText("Current Job: " + p.getCurrent_job());

        // Age Logic (Simple display of DOB for now, or calc age)
        tvAge.setText("Age: " + p.getDob()); // You can change this to "24" if you want hardcoded for now

        // Hobbies
        if (p.getHobbies() != null) {
            String hobbiesStr = p.getHobbies().toString().replace("[", "").replace("]", "");
            tvHobbies.setText(hobbiesStr);
        }

        // Image Logic
        String profileName = p.getProfile();
        int resId = 0;
        if (profileName != null && !profileName.isEmpty()) {
            resId = getResources().getIdentifier(profileName.toLowerCase(), "drawable", getPackageName());
        }

        if (resId != 0) {
            ivProfile.setImageResource(resId);
        } else {
            ivProfile.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}