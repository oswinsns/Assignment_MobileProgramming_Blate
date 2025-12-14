package com.example.aol_blate_mobprog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aol_blate_mobprog.models.Person;
import com.example.aol_blate_mobprog.models.User;
import com.example.aol_blate_mobprog.FirestoreManager;
import com.example.aol_blate_mobprog.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    // Views
    private TextView tvNameAge, tvJob, tvLocation, tvTitle;
    private ImageView ivProfile;
    private Button btnLike, btnDislike;
    private View cardLayout;

    // Data
    private List<Person> candidateList = new ArrayList<>();
    private int currentIndex = 0;
    private FirestoreManager firestoreManager;
    private boolean currentUserGender; // To store the logged-in user's gender

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        // 1. Initialize Views
        // Note: Because we used <include>, we find views directly if IDs are unique
        tvNameAge = findViewById(R.id.NameageCardTV);
        tvJob = findViewById(R.id.JobCardTV);
        tvLocation = findViewById(R.id.LocationCardTV);
        ivProfile = findViewById(R.id.ProfileCardImg);
        btnLike = findViewById(R.id.LikeDiscoverBtn);
        btnDislike = findViewById(R.id.DislikeDiscoverBtn);
        cardLayout = findViewById(R.id.layoutCardPerson); // The whole card view
        tvTitle = findViewById(R.id.TitleDiscoverTV);

        firestoreManager = FirestoreManager.getInstance();

        // 2. Setup Buttons
        btnLike.setOnClickListener(v -> handleSwipe(true));
        btnDislike.setOnClickListener(v -> handleSwipe(false));

        // 3. Load Data
        loadInitialData();
    }

    private void loadInitialData() {
        // Step A: Get Current User to know their Gender
        firestoreManager.getCurrentUser(new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                User currentUser = (User) result;
                currentUserGender = currentUser.isGender(); // Assuming getter exists

                // Step B: Once we know gender, fetch candidates
                fetchCandidates();
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(DiscoverActivity.this, "Error loading profile: " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCandidates() {
        firestoreManager.getAllCandidates(new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                List<Person> allPeople = (List<Person>) result;

                // Step C: Filter for Opposite Gender
                candidateList.clear();
                for (Person p : allPeople) {
                    // If User is Male (true), we want Female (false) -> Logic: user != person
                    if (p.isGender() != currentUserGender) {
                        candidateList.add(p);
                    }
                }

                if (candidateList.isEmpty()) {
                    showEmptyState();
                } else {
                    currentIndex = 0;
                    renderCard();
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(DiscoverActivity.this, "Error: " + e, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void renderCard() {
        if (currentIndex >= candidateList.size()) {
            showEmptyState();
            return;
        }

        Person person = candidateList.get(currentIndex);

        // 1. Set Text
        // Calculate Age (simplified for now, or just show DOB)
        String nameAndAge = person.getName() + ", " + getAgeFromDob(person.getDob());
        tvNameAge.setText(nameAndAge);
        tvJob.setText(person.getCurrent_job());
        tvLocation.setText(person.getDomicile());

        // 2. Set Image (Using your 3-Way Logic)
        String profileData = person.getProfile();

        // A. URL
        if (profileData.startsWith("http")) {
            Glide.with(this).load(profileData).centerCrop().into(ivProfile);
        }
        // B. Local File
        else if (profileData.startsWith("/")) {
            Bitmap myImage = ImageHelper.loadImageFromPath(profileData);
            if (myImage != null) ivProfile.setImageBitmap(myImage);
        }
        // C. Drawable Resource (avatar_1)
        else {
            int resId = getResources().getIdentifier(profileData, "drawable", getPackageName());
            if (resId != 0) {
                ivProfile.setImageResource(resId);
            } else {
                ivProfile.setImageResource(R.drawable.ic_launcher_background); // Fallback
            }
        }
    }

    private void handleSwipe(boolean isLike) {
        if (currentIndex >= candidateList.size()) return;

        Person currentPerson = candidateList.get(currentIndex);

        // 1. Save to Firestore
        firestoreManager.saveSwipeAction(String.valueOf(currentPerson.getId()), isLike);

        // 2. Visual Feedback
        String message = isLike ? "Liked " : "Disliked ";
        Toast.makeText(this, message + currentPerson.getName(), Toast.LENGTH_SHORT).show();

        // 3. Move to Next
        currentIndex++;
        renderCard();
    }

    private void showEmptyState() {
        tvTitle.setText("No more people!");
        cardLayout.setVisibility(View.GONE); // Hide the card
        btnLike.setEnabled(false);
        btnDislike.setEnabled(false);
    }

    // Simple helper to calculate age (Optional)
    private String getAgeFromDob(String dob) {
        // You can implement real date logic here. 
        // For now, returning a placeholder or extracting year.
        if(dob.length() >= 4) {
            int year = Integer.parseInt(dob.substring(0, 4));
            int currentYear = 2025;
            return String.valueOf(currentYear - year);
        }
        return "20";
    }
}