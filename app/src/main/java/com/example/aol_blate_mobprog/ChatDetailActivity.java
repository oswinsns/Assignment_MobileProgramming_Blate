package com.example.aol_blate_mobprog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aol_blate_mobprog.models.Message;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private ArrayList<Message> messageList;
    private EditText etInput;
    private ImageView btnSend, btnBack;
    private TextView tvName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // 1. Initialize Views
        recyclerView = findViewById(R.id.rvMessages);
        etInput = findViewById(R.id.etMessageInput);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        tvName = findViewById(R.id.tvChatName);

        // 2. Get Name from Intent (Passed from ChatActivity)
        name = getIntent().getStringExtra("userName");
        if(name != null) tvName.setText(name);

        // 3. Setup RecyclerView
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 4. Load Hardcoded Data
        loadDummyMessages();

        // 5. Setup Send Button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etInput.getText().toString().trim();
                if (!content.isEmpty()) {
                    sendMessage(content);
                }
            }
        });

        // 6. Setup Back Button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadDummyMessages() {
        // Matches your screenshot
        messageList.add(new Message("Hey! Saw your profile and you seem really cool. What are you up to this weekend?", "10:30 AM", false));
        messageList.add(new Message("Hey " + name + " ! Thanks 😊 Just chilling this weekend. Maybe some hiking. How about you?", "10:32 AM", true));
        messageList.add(new Message("Hiking sounds awesome! I was thinking of checking out that new cafe downtown. We should go sometime!", "10:33 AM", false));

        adapter.notifyDataSetChanged();
    }

    private void sendMessage(String content) {
        // Get current time
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

        // Add new message (isSentByMe = true)
        messageList.add(new Message(content, currentTime, true));

        // Update list and scroll to bottom
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.smoothScrollToPosition(messageList.size() - 1);

        // Clear input
        etInput.setText("");
    }
}