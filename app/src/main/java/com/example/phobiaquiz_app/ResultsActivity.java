package com.example.phobiaquiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ResultsActivity extends AppCompatActivity {

    HashMap<String,String> userTestResults;     // data passed in from user quiz
    String userName;
    TextView tvUserName;
    TextView tvResults;
    TextView tvPhobia;
    Button submitButton;

    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    QuizResultsDatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userTestResults = new HashMap<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvUserName = findViewById(R.id.tvUserName);
        tvResults = findViewById(R.id.tvResults);
        submitButton = findViewById(R.id.uploadButton);

        //TODO: create and link a phobia text view for that user, and calculate it for them
        tvPhobia = findViewById(R.id.tvPhobia);
        // calculate user phobia here, set this field to result
        // then use info to pass to db

        String testPhobia = "germophobe";
        tvPhobia.setText(testPhobia);

        // Use intent to retrieve data hashmap
        Intent intent = getIntent();    // create intent for data retrieval
        userTestResults = (HashMap<String, String>) intent.getSerializableExtra("data");
        userName = intent.getStringExtra("userName");   // retrieve username data field

        tvUserName.setText("User Name: " + userName);

        tvResults.append("\n\n");
        for(int i = 1;i <= userTestResults.size(); i++) {
            String temp = "picture" + i;

            tvResults.append(userTestResults.get(temp) + "\n");
        }
        //Set<String> keySet = userTestResults.keySet();
        //tvResults.append(keySet.toString());





        //-------------------------------------------------------------------------------------
        // Database code block
        mydb = new QuizResultsDatabaseHelper(this);

        // testing out db data retrieval
        tvResults.setText("");

        // display to user anonymized latest quiz results from past quizes
        ArrayList array_list = mydb.getAllQuizResults();
        for(int i = 0; i < array_list.size(); i++) {
            tvResults.append(array_list.get(i).toString() + "\n");
        }

        submitButton.setOnClickListener(v -> {
            Toast.makeText(this, "Data uploaded to database...", Toast.LENGTH_LONG).show();
            mydb.insertQuizResults(userName,testPhobia);
        });



        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        //obj = (ListView)findViewById(R.id.listView1);
        //obj.setAdapter(arrayAdapter);
        //obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        /*
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                int id_To_Search = position + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayQuizResults.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

         */


    }
}