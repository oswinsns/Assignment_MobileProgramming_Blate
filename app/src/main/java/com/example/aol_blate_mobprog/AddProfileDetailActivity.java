package com.example.aol_blate_mobprog;

import android.app.Dialog; // Import untuk Dialog
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color; // Import untuk warna transparan
import android.graphics.drawable.ColorDrawable; // Import drawable
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Import ImageView
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView; // Import TextView
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddProfileDetailActivity extends AppCompatActivity {

    private EditText etUsername, etDob, etAddress;
    private Spinner spHobbies, spJob, spReligion;
    private RadioGroup rgGender;
    private Button btnConfirm;

    // Variabel sinyal edit mode
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addprofiledetail);

        etUsername = findViewById(R.id.usernameInput);
        etDob = findViewById(R.id.dobInput);
        etAddress = findViewById(R.id.addressInput);
        spHobbies = findViewById(R.id.hobbiesDropdown);
        spJob = findViewById(R.id.jobDropdown);
        spReligion = findViewById(R.id.religionDropdown);
        rgGender = findViewById(R.id.genderGroup);
        btnConfirm = findViewById(R.id.confirmButton);

        // 1. Tangkap Sinyal
        isEditMode = getIntent().getBooleanExtra("IS_EDIT_MODE", false);

        setupSpinners();

        // 2. Load data lama (termasuk AGAMA)
        loadSavedData();

        btnConfirm.setOnClickListener(v -> {
            if(validateInput()) {
                saveDataToMemory();

                Intent intent;
                if (isEditMode) {
                    // Mode Edit -> Balik ke Profile
                    intent = new Intent(AddProfileDetailActivity.this, ProfileActivity.class);
                } else {
                    // Mode Register -> Lanjut Discover
                    intent = new Intent(AddProfileDetailActivity.this, DiscoverActivity.class);
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // 3. PANGGIL FUNGSI HELP DI SINI
        showHelpDialog();
    }

    private void setupSpinners() {
        String[] hobbies = new String[]{"Traveling", "Culinary", "Gaming", "Reading", "Sports"};
        ArrayAdapter<String> hobbiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hobbies);
        hobbiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHobbies.setAdapter(hobbiesAdapter);

        ArrayAdapter<CharSequence> jobAdapter = ArrayAdapter.createFromResource(this, R.array.job_list, android.R.layout.simple_spinner_item);
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJob.setAdapter(jobAdapter);

        ArrayAdapter<CharSequence> religionAdapter = ArrayAdapter.createFromResource(this, R.array.religion_list, android.R.layout.simple_spinner_item);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReligion.setAdapter(religionAdapter);
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);

        String savedName = prefs.getString("saved_name", "");
        String savedDob = prefs.getString("saved_dob", "");
        String savedAddress = prefs.getString("saved_address", "");
        String savedGender = prefs.getString("saved_gender", "Male");
        String savedHobby = prefs.getString("saved_hobby", "");
        String savedReligion = prefs.getString("saved_religion", "");

        if (!savedName.equals("No Name") && !savedName.isEmpty()) {
            etUsername.setText(savedName);
            etDob.setText(savedDob);
            etAddress.setText(savedAddress);

            if (savedGender.equalsIgnoreCase("Female")) {
                rgGender.check(R.id.femaleOption);
            } else {
                rgGender.check(R.id.maleOption);
            }

            if (!savedHobby.isEmpty()) {
                ArrayAdapter adapter = (ArrayAdapter) spHobbies.getAdapter();
                int position = adapter.getPosition(savedHobby);
                if (position >= 0) spHobbies.setSelection(position);
            }

            if (!savedReligion.isEmpty()) {
                ArrayAdapter adapter = (ArrayAdapter) spReligion.getAdapter();
                int position = adapter.getPosition(savedReligion);
                if (position >= 0) spReligion.setSelection(position);
            }
        }
    }

    private boolean validateInput() {
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("Username required");
            return false;
        }
        if (etDob.getText().toString().isEmpty()) {
            etDob.setError("Date of Birth required");
            return false;
        }
        return true;
    }

    private void saveDataToMemory() {
        String name = etUsername.getText().toString();
        String dob = etDob.getText().toString();
        String address = etAddress.getText().toString();
        String hobby = spHobbies.getSelectedItem().toString();
        String religion = spReligion.getSelectedItem().toString();

        int selectedId = rgGender.getCheckedRadioButtonId();
        String gender = "Male";
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            gender = selectedRadioButton.getText().toString();
        }

        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("saved_name", name);
        editor.putString("saved_dob", dob);
        editor.putString("saved_address", address);
        editor.putString("saved_hobby", hobby);
        editor.putString("saved_gender", gender);
        editor.putString("saved_religion", religion);

        editor.apply();

        Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
    }

    // --- FUNGSI POPUP HELP (Sama persis dengan page lain) ---
    private void showHelpDialog(){
        ImageView btnHelp = findViewById(R.id.helpIcon); // ID di XML kamu adalah helpIcon

        if(btnHelp != null) {
            btnHelp.setOnClickListener(v -> {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_help); // Pastikan layout ini ada

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                TextView tvTitle = dialog.findViewById(R.id.tvDialogTitle);
                TextView tvMessage = dialog.findViewById(R.id.tvDialogMessage);
                Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

                // Set Teks sesuai konteks halaman ini
                if (tvTitle != null) tvTitle.setText("Profile Details");
                if (tvMessage != null) tvMessage.setText("Please fill in your personal information accurately. This information will be displayed on your profile card.");

                if (btnClose != null) {
                    btnClose.setOnClickListener(view -> dialog.dismiss());
                }
                dialog.show();
            });
        }
    }
}