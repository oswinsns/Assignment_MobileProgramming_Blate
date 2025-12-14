package com.example.aol_blate_mobprog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aol_blate_mobprog.models.Person;
import com.example.aol_blate_mobprog.models.User;
import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    private FirestoreManager firestoreManager;
    private List<Person> peopleList;
    private int currentIndex = 0;

    // UI Components
    private ImageView profileImage;
    private TextView tvName, tvJob, tvNoData; // Added tvNoData (Optional)
    private View btnLike, btnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        firestoreManager = FirestoreManager.getInstance();
        peopleList = new ArrayList<>();

        // 1. Initialize Views (Make sure these IDs match your XML!)
        profileImage = findViewById(R.id.ProfileCardImg);
        tvName = findViewById(R.id.NameageCardTV);
        tvJob = findViewById(R.id.JobCardTV);

        // Note: We REMOVED tvAge because we will add age to tvName

        btnLike = findViewById(R.id.LikeDiscoverBtn);
        btnDislike = findViewById(R.id.DislikeDiscoverBtn);

        // (Optional) If you have a "No more users" textview in XML, find it here
        // tvNoData = findViewById(R.id.tv_no_data);

        // 2. Setup Buttons
        if (btnLike != null) btnLike.setOnClickListener(v -> handleSwipe(true));
        if (btnDislike != null) btnDislike.setOnClickListener(v -> handleSwipe(false));

        setupNavbar();
        loadInitialData();
    }

    private void loadInitialData() {
        firestoreManager.getCurrentUser(new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                User currentUser = (User) result;
                List<String> ignoredIds = new ArrayList<>();
                if (currentUser.getAccepted() != null) ignoredIds.addAll(currentUser.getAccepted());
                if (currentUser.getRejected() != null) ignoredIds.addAll(currentUser.getRejected());

                // Pass gender to filter
                fetchAndFilterPeople(ignoredIds, currentUser.isGender());
            }

            @Override
            public void onFailure(Exception e) {
                // Fallback: Show everyone if user load fails
                fetchAndFilterPeople(new ArrayList<>(), true);
            }
        });
    }

    private void fetchAndFilterPeople(List<String> ignoredIds, boolean currentUserGender) {
        firestoreManager.getAllCandidates(new FirestoreManager.FirestoreCallback() {
            @Override
            public void onSuccess(Object result) {
                List<Person> allCandidates = (List<Person>) result;
                peopleList.clear();

                for (Person p : allCandidates) {
                    boolean isNotSwiped = !ignoredIds.contains(String.valueOf(p.getId()));
                    boolean isOppositeGender = (p.isGender() != currentUserGender);

                    if (isNotSwiped && isOppositeGender) {
                        peopleList.add(p);
                    }
                }

                if (peopleList.isEmpty()) {
                    // LIST IS EMPTY -> HIDE EVERYTHING
                    toggleEmptyState(true);
                } else {
                    // LIST HAS PEOPLE -> SHOW EVERYTHING
                    toggleEmptyState(false);
                    currentIndex = 0;
                    showPerson(currentIndex);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(DiscoverActivity.this, "Error fetching people", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPerson(int index) {
        // Double check if we ran out of people while swiping
        if (peopleList.isEmpty() || index >= peopleList.size()) {
            toggleEmptyState(true);
            return;
        }

        Person person = peopleList.get(index);

        // Calculate Age
        String age = "20";
        if (person.getDob() != null && person.getDob().length() >= 4) age = "21";

        if (tvName != null) tvName.setText(person.getName() + ", " + age);
        if (tvJob != null) tvJob.setText(person.getCurrent_job());

        String profileName = person.getProfile();
        int resId = 0;
        if (profileName != null && !profileName.isEmpty()) {
            resId = getResources().getIdentifier(profileName.toLowerCase(), "drawable", getPackageName());
        }
        if (resId != 0) profileImage.setImageResource(resId);
        else profileImage.setImageResource(R.drawable.ic_launcher_background);
    }

    private void handleSwipe(boolean isLike) {
        if (peopleList.isEmpty() || currentIndex >= peopleList.size()) return;

        Person currentPerson = peopleList.get(currentIndex);
        String targetId = String.valueOf(currentPerson.getId());

        firestoreManager.saveSwipeAction(targetId, isLike);

        currentIndex++; // Move next
        showPerson(currentIndex); // Update screen
    }

    // --- NEW HELPER METHOD TO HIDE/SHOW UI ---
    private void toggleEmptyState(boolean isEmpty) {
        int visibility = isEmpty ? View.GONE : View.VISIBLE;

        // Hide/Show the card elements
        if (profileImage != null) profileImage.setVisibility(visibility);
        if (tvName != null) tvName.setVisibility(visibility);
        if (tvJob != null) tvJob.setVisibility(visibility);

        // Hide/Show the buttons
        if (btnLike != null) btnLike.setVisibility(visibility);
        if (btnDislike != null) btnDislike.setVisibility(visibility);

        // Optional: Show a "No Users" text if everything is gone
        if (tvNoData != null) {
            tvNoData.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        } else if (isEmpty) {
            // If you don't have a specific textview, just toast
            Toast.makeText(this, "No more profiles nearby!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupNavbar() {
        ImageView navProfile = findViewById(R.id.ProfileNav);
        ImageView navHistory = findViewById(R.id.HistoryNav);
        ImageView navChat = findViewById(R.id.ChatNav);
        if (navProfile != null) navProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        if (navHistory != null) navHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        if (navChat != null) navChat.setOnClickListener(v -> startActivity(new Intent(this, ChatActivity.class)));
    }
}