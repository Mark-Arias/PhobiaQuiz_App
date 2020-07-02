package com.example.phobiaquiz_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

/**
 * Main activity for this project, handles user login interactions
 */
public class LoginActivity extends AppCompatActivity {


    Context context;
    private Button signUpButton;
    private Button loginButton;
    private HashMap<String,String> credentialsDictionary = null;
    private EditText userNameField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = getApplicationContext();
        // -----------------------------------------------------------------------------------------
        // perform Hashmap data operations
        credentialsDictionary = new HashMap<String,String>();                                       // initialize the hashmap
        credentialsDictionary.put("mark","123");                                                  //
        if((HashMap<String, String>) getIntent().getSerializableExtra("data")!= null) {       // attempt to retrieve an existing hashmap existence if it already exists or was modified in another activity
            credentialsDictionary = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        }

        if(credentialsDictionary != null) {
            int s = credentialsDictionary.size();
        }

        // -----------------------------------------------------------------------------------------
        // initialize and wire up the editText UI components
        userNameField = findViewById(R.id.userNameField);
        passwordField = findViewById(R.id.passwordField);

        // -----------------------------------------------------------------------------------------
        // Code controls the operation of the sign up button
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener((v) -> {                                                    // making use of a lambda expression to simplify click listener syntax
            Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
            signUpIntent.putExtra("data", credentialsDictionary);                             // send the credentials dictionary over to the sign up activity page for modification upon successful sign up
            startActivity(signUpIntent);
        });
        // -----------------------------------------------------------------------------------------
        // code controls the operations of the log in button
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener((v) -> {
            if(credentialsDictionary.containsKey(userNameField.getText().toString())) {
                Intent welcomeIntent = new Intent(getApplicationContext(),MainActivity.class);  //launch main activity
                welcomeIntent.putExtra("userInfo",userNameField.getText().toString());        // send user data over to next activity that needs it
                startActivity(welcomeIntent);
            } else {                                                                                // login not sucessfull
                Toast toast = Toast.makeText(context, "Try again. Login not successful.", Toast.LENGTH_LONG);
                toast.show();
                passwordField.getText().clear();
            }
        });
        // -----------------------------------------------------------------------------------------

    }
}
