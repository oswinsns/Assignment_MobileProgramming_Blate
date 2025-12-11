package com.example.aol_blate_mobprog;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddProfileDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addprofiledetail);


        Spinner hobbiesSpinner = findViewById(R.id.hobbiesDropdown);
        Spinner jobSpinner = findViewById(R.id.jobDropdown);
        Spinner religionSpinner = findViewById(R.id.religionDropdown);

        // Hobbies Spinner
        ArrayAdapter<CharSequence> hobbiesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.hobbies_list,
                android.R.layout.simple_spinner_item
        );
        hobbiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hobbiesSpinner.setAdapter(hobbiesAdapter);

        // Job Spinner
        ArrayAdapter<CharSequence> jobAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.job_list,
                android.R.layout.simple_spinner_item
        );
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobSpinner.setAdapter(jobAdapter);

        // Religion Spinner
        ArrayAdapter<CharSequence> religionAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.religion_list,
                android.R.layout.simple_spinner_item
        );
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religionSpinner.setAdapter(religionAdapter);


    }


}
