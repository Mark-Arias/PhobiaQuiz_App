package com.example.phobiaquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity {

    HashMap<String,String> userTestResults;
    String userName;
    TextView tvUserName;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userTestResults = new HashMap<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvUserName = findViewById(R.id.tvUserName);
        tvResults = findViewById(R.id.tvResults);

        // Use intent to retrieve data hashmap
        Intent intent = getIntent();    // create intent for data retrieval
        userTestResults = (HashMap<String, String>) intent.getSerializableExtra("data");
        userName = intent.getStringExtra("userName");   // retrieve username data field

        tvUserName.setText("User Name: " + userName);

        tvResults.append("Results\n\n");
        for(int i = 1;i <= userTestResults.size(); i++) {
            String temp = "picture" + i;

            tvResults.append(userTestResults.get(temp) + "\n");
        }
        Set<String> keySet = userTestResults.keySet();
        tvResults.append(keySet.toString());


    }
}